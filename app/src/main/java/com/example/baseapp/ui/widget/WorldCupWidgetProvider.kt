package com.worldcup.app.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.worldcup.app.R
import com.worldcup.app.data.local.database.AppDatabase
import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.ui.worker.WorldCupSyncWorker
import com.worldcup.app.utils.decodeUnicodeFlag
import com.worldcup.app.utils.toDisplayTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorldCupWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        enqueueImmediateSync(context)
        appWidgetIds.forEach { appWidgetId ->
            updateWidgetAsync(context, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )

        when (intent.action) {
            ACTION_NEXT_MATCH -> {
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    handleMoveAction(context, appWidgetId, step = 1)
                }
            }

            ACTION_PREVIOUS_MATCH -> {
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    handleMoveAction(context, appWidgetId, step = -1)
                }
            }

            ACTION_REFRESH_WIDGET, ACTION_PINNED -> {
                enqueueImmediateSync(context)
                updateAllWidgets(context)
            }
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        appWidgetIds.forEach { appWidgetId ->
            WorldCupWidgetPrefs.clearWidget(context, appWidgetId)
        }
        super.onDeleted(context, appWidgetIds)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        updateWidgetAsync(context, appWidgetId)
    }

    companion object {
        const val ACTION_NEXT_MATCH = "com.worldcup.app.ACTION_NEXT_MATCH"
        const val ACTION_PREVIOUS_MATCH = "com.worldcup.app.ACTION_PREVIOUS_MATCH"
        const val ACTION_REFRESH_WIDGET = "com.worldcup.app.ACTION_REFRESH_WIDGET"
        const val ACTION_PINNED = "com.worldcup.app.ACTION_PINNED"
        const val SYNC_WORK_NAME = "world_cup_widget_sync"

        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, WorldCupWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

            appWidgetIds.forEach { appWidgetId ->
                updateWidgetAsync(context, appWidgetId)
            }
        }

        fun enqueueImmediateSync(context: Context) {
            // Keep the sync pending when the device is offline; the widget can still render cached Room data.
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val request = OneTimeWorkRequestBuilder<WorldCupSyncWorker>()
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }

        private fun handleMoveAction(
            context: Context,
            appWidgetId: Int,
            step: Int
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                val matches = loadWidgetMatches(context)

                WorldCupWidgetPrefs.moveIndex(
                    context = context,
                    appWidgetId = appWidgetId,
                    step = step,
                    total = matches.size
                )

                updateWidget(context, appWidgetId, matches)
            }
        }

        fun updateWidgetAsync(
            context: Context,
            appWidgetId: Int
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                updateWidget(context, appWidgetId, loadWidgetMatches(context))
            }
        }

        private suspend fun loadWidgetMatches(context: Context): List<WorldCupMatchEntity> {
            val today = LocalDate.now()
            return AppDatabase.getDatabase(context)
                .worldCupMatchDao()
                .getWidgetMatches(
                    today = today.toString(),
                    yesterday = today.minusDays(1).toString()
                )
        }

        private fun updateWidget(
            context: Context,
            appWidgetId: Int,
            matches: List<WorldCupMatchEntity>
        ) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.widget_world_cup)

            val savedIndex = WorldCupWidgetPrefs.getCurrentIndex(context, appWidgetId)
            val safeIndex = resolveDisplayIndex(context, appWidgetId, matches, savedIndex)
            if (matches.isNotEmpty() && safeIndex != savedIndex) {
                WorldCupWidgetPrefs.saveCurrentIndex(context, appWidgetId, safeIndex)
            }

            val match = matches.getOrNull(safeIndex)

            if (match == null) {
                renderEmpty(views)
            } else {
                renderMatch(views, match)
            }

            views.setOnClickPendingIntent(
                R.id.tvNext,
                createActionPendingIntent(
                    context = context,
                    appWidgetId = appWidgetId,
                    action = ACTION_NEXT_MATCH,
                    requestCode = appWidgetId + 1000
                )
            )

            views.setOnClickPendingIntent(
                R.id.tvPrevious,
                createActionPendingIntent(
                    context = context,
                    appWidgetId = appWidgetId,
                    action = ACTION_PREVIOUS_MATCH,
                    requestCode = appWidgetId + 2000
                )
            )

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun resolveDisplayIndex(
            context: Context,
            appWidgetId: Int,
            matches: List<WorldCupMatchEntity>,
            savedIndex: Int
        ): Int {
            if (matches.isEmpty()) return 0
            if (WorldCupWidgetPrefs.hasCurrentIndex(context, appWidgetId)) {
                return savedIndex.coerceIn(0, matches.lastIndex)
            }

            // First render after pinning: show the earliest match on the user's local date today.
            val today = LocalDate.now().toString()
            return matches.indexOfFirst { it.localMatchDate == today }
                .takeIf { it >= 0 }
                ?: 0
        }

        private fun renderEmpty(views: RemoteViews) {
            views.setTextViewText(R.id.tvTitle, "World Cup 2026")
            views.setTextViewText(R.id.tvTimeStart, "Loading matches...")
            views.setTextViewText(R.id.tvHomeFlag, "")
            views.setTextViewText(R.id.tvAwayFlag, "")
            views.setTextViewText(R.id.tvHomeTeamName, "")
            views.setTextViewText(R.id.tvAwayTeamName, "")
            views.setTextViewText(R.id.tvScoreOrVs, "VS")
        }

        private fun renderMatch(
            views: RemoteViews,
            match: WorldCupMatchEntity
        ) {
            views.setTextViewText(R.id.tvTitle, match.round)
            views.setTextViewText(
                R.id.tvTimeStart,
                "${match.localMatchDate ?: match.date} ${match.kickoffMillis?.toDisplayTime() ?: match.time}"
            )
            views.setTextViewText(R.id.tvHomeFlag, match.homeTeamFlag.decodeUnicodeFlag())
            views.setTextViewText(R.id.tvAwayFlag, match.awayTeamFlag.decodeUnicodeFlag())
            views.setTextViewText(R.id.tvHomeTeamName, match.homeTeamName)
            views.setTextViewText(R.id.tvAwayTeamName, match.awayTeamName)

            val scoreText = if (match.homeScore != null && match.awayScore != null) {
                "${match.homeScore}-${match.awayScore}"
            } else {
                "VS"
            }

            views.setTextViewText(R.id.tvScoreOrVs, scoreText)
        }

        private fun createActionPendingIntent(
            context: Context,
            appWidgetId: Int,
            action: String,
            requestCode: Int
        ): PendingIntent {
            val intent = Intent(context, WorldCupWidgetProvider::class.java).apply {
                this.action = action
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
