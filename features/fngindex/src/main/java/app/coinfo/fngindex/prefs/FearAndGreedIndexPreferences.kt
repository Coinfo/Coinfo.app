package app.coinfo.fngindex.prefs

import android.content.Context
import androidx.core.content.edit
import app.coinfo.fngindex.model.FearAndGreedIndex

class FearAndGreedIndexPreferences(
    appContext: Context
) : Preferences {

    private val preferences = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setFearAndGreedIndex(index: FearAndGreedIndex) {
        preferences.edit(commit = true) {
            putString(PREFERENCES_KEY_VALUE, index.value)
            putString(PREFERENCES_KEY_VALUE_NAME, index.valueName)
            putLong(PREFERENCES_KEY_LAST_UPDATE_DATE, index.lastUpdateDateMillis)
            putLong(PREFERENCES_KEY_NEXT_UPDATE_DATE, index.nextUpdateDateSeconds)
        }
    }

    override fun getFearAndGreedIndex() = if (hasSavedFearAndGreedIndex()) {
        FearAndGreedIndex.FearAndGreedIndexSuccess(
            value = preferences.getString(PREFERENCES_KEY_VALUE, "") ?: "",
            valueName = preferences.getString(PREFERENCES_KEY_VALUE_NAME, "") ?: "",
            lastUpdateDateMillis = preferences.getLong(PREFERENCES_KEY_LAST_UPDATE_DATE, 0L),
            nextUpdateDateSeconds = preferences.getLong(PREFERENCES_KEY_NEXT_UPDATE_DATE, 0L),
        )
    } else FearAndGreedIndex.FearAndGreedIndexEmpty

    override fun getLastUpdateDate() = preferences.getLong(PREFERENCES_KEY_LAST_UPDATE_DATE, 0L)

    override fun getNextUpdateDate() = preferences.getLong(PREFERENCES_KEY_NEXT_UPDATE_DATE, 0L)

    override fun getFearAndGreedIndexValue() = preferences.getString(PREFERENCES_KEY_VALUE, "") ?: ""

    override fun getFearAndGreedIndexValueName() = preferences.getString(PREFERENCES_KEY_VALUE_NAME, "") ?: ""

    override fun hasSavedFearAndGreedIndex() =
        preferences.contains(PREFERENCES_KEY_VALUE) &&
            preferences.contains(PREFERENCES_KEY_VALUE_NAME) &&
            preferences.contains(PREFERENCES_KEY_LAST_UPDATE_DATE) &&
            preferences.contains(PREFERENCES_KEY_NEXT_UPDATE_DATE)

    override fun reset() {
        preferences.edit(commit = true) {
            remove(PREFERENCES_KEY_VALUE)
            remove(PREFERENCES_KEY_VALUE_NAME)
            remove(PREFERENCES_KEY_LAST_UPDATE_DATE)
            remove(PREFERENCES_KEY_NEXT_UPDATE_DATE)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "fng.prefs"
        private const val PREFERENCES_KEY_VALUE = "key_fng_value"
        private const val PREFERENCES_KEY_VALUE_NAME = "key_fng_value_name"
        private const val PREFERENCES_KEY_LAST_UPDATE_DATE = "key_fng_last_update_date"
        private const val PREFERENCES_KEY_NEXT_UPDATE_DATE = "key_fng_next_update_date"
    }
}
