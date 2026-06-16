package com.worldcup.app.ui.page.world_cup_widget

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.worldcup.app.data.local.database.AppDatabase
import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.databinding.ActivityWorldCupWidgetBinding
import com.worldcup.app.ui.base.BaseActivity
import com.worldcup.app.ui.widget.WorldCupWidgetProvider
import com.worldcup.app.utils.decodeUnicodeFlag
import com.worldcup.app.utils.toDisplayTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ActivityWorldCupWidget : BaseActivity<ActivityWorldCupWidgetBinding>() {
    private var previewMatches: List<WorldCupMatchEntity> = emptyList()
    private var previewIndex: Int = 0

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityWorldCupWidgetBinding {
        return ActivityWorldCupWidgetBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {
    }

    override fun initView() {
        renderEmptyPreview()
        loadTodayPreview()
    }

    override fun initAction() {
        binding.widgetContainer.setOnClickListener {
            pinWidget()
        }
        binding.tvPrevious.setOnClickListener {
            movePreview(step = -1)
        }
        binding.tvNext.setOnClickListener {
            movePreview(step = 1)
        }
    }


    override fun initObserver() {
    }

    private fun loadTodayPreview() {
        lifecycleScope.launch {
            val today = LocalDate.now()
            val matches = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(this@ActivityWorldCupWidget)
                    .worldCupMatchDao()
                    .getWidgetMatches(
                        today = today.toString(),
                        yesterday = today.minusDays(1).toString()
                    )
            }

            previewMatches = matches
            previewIndex = matches.indexOfFirst { it.localMatchDate == today.toString() }
                .takeIf { it >= 0 }
                ?: 0

            if (matches.isEmpty()) {
                WorldCupWidgetProvider.enqueueImmediateSync(this@ActivityWorldCupWidget)
                renderEmptyPreview()
            } else {
                renderMatchPreview(matches[previewIndex])
            }
        }
    }

    private fun movePreview(step: Int) {
        if (previewMatches.isEmpty()) return
        previewIndex = (previewIndex + step).floorMod(previewMatches.size)
        renderMatchPreview(previewMatches[previewIndex])
    }

    private fun renderEmptyPreview() {
        binding.tvTitle.text = "World Cup 2026"
        binding.tvTimeStart.text = "Loading matches..."
        binding.tvHomeFlag.text = ""
        binding.tvAwayFlag.text = ""
        binding.tvHomeTeamName.text = ""
        binding.tvAwayTeamName.text = ""
        binding.tvScoreOrVs.text = "VS"
    }

    private fun renderMatchPreview(match: WorldCupMatchEntity) {
        binding.tvTitle.text = match.round
        binding.tvTimeStart.text =
            "${match.localMatchDate ?: match.date} ${match.kickoffMillis?.toDisplayTime() ?: match.time}"
        binding.tvHomeFlag.text = match.homeTeamFlag.decodeUnicodeFlag()
        binding.tvAwayFlag.text = match.awayTeamFlag.decodeUnicodeFlag()
        binding.tvHomeTeamName.text = match.homeTeamName
        binding.tvAwayTeamName.text = match.awayTeamName
        binding.tvScoreOrVs.text = if (match.homeScore != null && match.awayScore != null) {
            "${match.homeScore}-${match.awayScore}"
        } else {
            "VS"
        }
    }

    private fun pinWidget() {
        val appWidgetManager = android.appwidget.AppWidgetManager.getInstance(this)
        if (!appWidgetManager.isRequestPinAppWidgetSupported) {
            showToast("Your device does not support pinning widgets")
            return
        }
        val provider = ComponentName(this, WorldCupWidgetProvider::class.java)

        val callbackIntent = Intent(this, WorldCupWidgetProvider::class.java).apply {
            action = WorldCupWidgetProvider.ACTION_PINNED
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val successCallback = PendingIntent.getBroadcast(
            this,
            1,
            callbackIntent,
            flags
        )
        appWidgetManager.requestPinAppWidget(
            provider,
            null,
            successCallback
        )
        Log.d("WidgetPinning", "Pinning widget requested")

    }

    private fun Int.floorMod(mod: Int): Int {
        return ((this % mod) + mod) % mod
    }

}
