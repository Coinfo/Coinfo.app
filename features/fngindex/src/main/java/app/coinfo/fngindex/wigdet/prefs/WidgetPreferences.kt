package app.coinfo.fngindex.wigdet.prefs

import android.content.Context
import androidx.core.content.edit
import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex

class WidgetPreferences(
    appContext: Context
) : Preferences {

    private val preferences = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override suspend fun setDailyFearAndGreedIndex(data: DailyFearAndGreedIndex) {
        preferences.edit(commit = true) {
            putInt(PREFERENCES_KEY_VALUE, data.value)
            putString(PREFERENCES_KEY_VALUE_NAME, data.valueName)
            putLong(PREFERENCES_KEY_LAST_UPDATE_DATE, data.lastUpdateDateMillis)
            putInt(PREFERENCES_KEY_NEXT_UPDATE_DATE, data.nextUpdateDateSeconds)
            putLong(PREFERENCES_KET_TIMESTAMP, data.timestampInMillis)
        }
    }

    override fun getDailyFearAndGreedIndex(): DailyFearAndGreedIndex? {
        val keys = listOf(
            PREFERENCES_KEY_VALUE,
            PREFERENCES_KEY_VALUE_NAME,
            PREFERENCES_KEY_LAST_UPDATE_DATE,
            PREFERENCES_KEY_NEXT_UPDATE_DATE,
            PREFERENCES_KET_TIMESTAMP
        )
        return if (contains(keys)) {
            DailyFearAndGreedIndex(
                value = preferences.getInt(PREFERENCES_KEY_VALUE, 0),
                valueName = preferences.getString(PREFERENCES_KEY_VALUE_NAME, "") ?: "",
                lastUpdateDateMillis = preferences.getLong(PREFERENCES_KEY_LAST_UPDATE_DATE, 0),
                nextUpdateDateSeconds = preferences.getInt(PREFERENCES_KEY_NEXT_UPDATE_DATE, 0),
                timestampInMillis = preferences.getLong(PREFERENCES_KET_TIMESTAMP, 0),
            )
        } else null
    }

    override fun removeDailyFearAndGreedIndex() {
        preferences.edit(commit = true) {
            remove(PREFERENCES_KEY_VALUE)
            remove(PREFERENCES_KEY_VALUE_NAME)
            remove(PREFERENCES_KEY_LAST_UPDATE_DATE)
            remove(PREFERENCES_KEY_NEXT_UPDATE_DATE)
            remove(PREFERENCES_KET_TIMESTAMP)
        }
    }

    private fun contains(keys: List<String>): Boolean {
        keys.forEach { key ->
            if (!preferences.contains(key)) {
                return false
            }
        }
        return true
    }

    companion object {
        private const val PREFERENCES_NAME = "fng.prefs"
        private const val PREFERENCES_KEY_VALUE = "key_daily_widget_value"
        private const val PREFERENCES_KEY_VALUE_NAME = "key_daily_widget_value_name"
        private const val PREFERENCES_KEY_LAST_UPDATE_DATE = "key_daily_widget_last_update_date"
        private const val PREFERENCES_KEY_NEXT_UPDATE_DATE = "key_daily_widget_next_update_date"
        private const val PREFERENCES_KET_TIMESTAMP = "key_daily_widget_timestamp"
    }
}
