package com.worldcup.app.ui.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.worldcup.app.BuildConfig
import com.worldcup.app.data.local.database.AppDatabase
import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.data.remote.api.APIWorldCup
import com.worldcup.app.ui.model.toEntity
import com.worldcup.app.ui.widget.WorldCupWidgetPrefs
import com.worldcup.app.ui.widget.WorldCupWidgetProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class WorldCupSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val api = createApi()
            val matchesResponse = api.getAllMatches()
            val teamsResponse = api.getTeams()

            if (!matchesResponse.isSuccessful || !teamsResponse.isSuccessful) {
                scheduleNextSync(DEFAULT_RETRY_DELAY_MINUTES)
                return Result.retry()
            }

            val matchesDto = matchesResponse.body()?.matches.orEmpty()
            val teamsDto = teamsResponse.body().orEmpty()
            val teamEntities = teamsDto.map { it.toEntity() }
            val matchEntities = matchesDto.map { it.toEntity(teamsDto) }

            val db = AppDatabase.getDatabase(applicationContext)
            db.worldCupTeamDao().insertTeams(teamEntities)
            db.worldCupMatchDao().replaceMatches(matchEntities)

            // Widgets render from Room, so they keep showing the last known data if the next sync is delayed.
            WorldCupWidgetPrefs.saveLastUpdateTime(
                context = applicationContext,
                timeMillis = System.currentTimeMillis()
            )

            WorldCupWidgetProvider.updateAllWidgets(applicationContext)
            scheduleNextSync(calculateNextDelayMinutes(matchEntities))

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            scheduleNextSync(DEFAULT_RETRY_DELAY_MINUTES)
            Result.retry()
        }
    }

    private fun createApi(): APIWorldCup {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIWorldCup::class.java)
    }

    private fun calculateNextDelayMinutes(matches: List<WorldCupMatchEntity>): Long {
        val today = LocalDate.now().toString()
        val now = System.currentTimeMillis()
        val todayMatches = matches.filter { it.localMatchDate == today }

        // No local-date match today, or every match today already has a score: slow sync is enough.
        if (todayMatches.isEmpty()) return 6 * 60

        val unfinishedToday = todayMatches.filter { it.homeScore == null || it.awayScore == null }
        if (unfinishedToday.isEmpty()) return 6 * 60

        // Check frequently from one hour before kickoff until about full time plus stoppage time.
        val hasActiveOrSoonMatch = unfinishedToday.any { match ->
            val kickoff = match.kickoffMillis ?: return@any false
            val minutesUntilKickoff = TimeUnit.MILLISECONDS.toMinutes(kickoff - now)
            val matchWindowEnd = kickoff + TimeUnit.MINUTES.toMillis(150)

            minutesUntilKickoff in 0..60 || now in kickoff..matchWindowEnd
        }

        return if (hasActiveOrSoonMatch) 15 else 60
    }

    private fun scheduleNextSync(delayMinutes: Long) {
        // One-time work lets us choose the next delay dynamically after each API result.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequestBuilder<WorldCupSyncWorker>()
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            NEXT_SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    companion object {
        private const val NEXT_SYNC_WORK_NAME = "world_cup_widget_next_sync"
        private const val DEFAULT_RETRY_DELAY_MINUTES = 15L
    }
}
