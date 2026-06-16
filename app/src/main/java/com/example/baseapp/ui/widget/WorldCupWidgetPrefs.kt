package com.worldcup.app.ui.widget

import android.content.Context

object WorldCupWidgetPrefs {
    private const val PREFS_NAME = "world_cup_widget_prefs"
    private const val KEY_LAST_UPDATE = "last_update_time"
    private const val KEY_INDEX_PREFIX = "widget_index_"

    fun getCurrentIndex(context: Context, appWidgetId: Int): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_INDEX_PREFIX + appWidgetId, 0)
    }

    fun hasCurrentIndex(context: Context, appWidgetId: Int): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .contains(KEY_INDEX_PREFIX + appWidgetId)
    }

    fun saveCurrentIndex(context: Context, appWidgetId: Int, index: Int) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_INDEX_PREFIX + appWidgetId, index)
            .apply()
    }

    fun moveIndex(context: Context, appWidgetId: Int, step: Int, total: Int): Int {
        if (total <= 0) {
            saveCurrentIndex(context, appWidgetId, 0)
            return 0
        }

        val currentIndex = getCurrentIndex(context, appWidgetId)
        val nextIndex = (currentIndex + step).floorMod(total)
        saveCurrentIndex(context, appWidgetId, nextIndex)
        return nextIndex
    }

    fun clearWidget(context: Context, appWidgetId: Int) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_INDEX_PREFIX + appWidgetId)
            .apply()
    }

    fun saveLastUpdateTime(context: Context, timeMillis: Long) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_LAST_UPDATE, timeMillis)
            .apply()
    }

    private fun Int.floorMod(mod: Int): Int {
        return ((this % mod) + mod) % mod
    }
}
