package app.coinfo.library.preferences

import android.content.Context
import app.coinfo.library.core.enums.TimeInterval

class CoinfoPreferences(
    appContext: Context
) : Preferences {

    private val sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveCurrency(currency: String) {
        sharedPreferences.edit().putString(KEY_CURRENCY, currency).apply()
    }

    override fun loadCurrency() =
        sharedPreferences.getString(KEY_CURRENCY, DEFAULT_CURRENCY) ?: DEFAULT_CURRENCY

    override fun saveTimeInterval(interval: TimeInterval) {
        sharedPreferences.edit().putString(KEY_CHANGE_TIMELINE, interval.uuid).apply()
    }

    override fun loadTimeInterval() = TimeInterval.fromUUID(
        sharedPreferences.getString(KEY_CHANGE_TIMELINE, TimeInterval.DAY.uuid)
    )

    companion object {
        private const val SHARED_PREFERENCES_NAME = "coinfo.prefs"
        private const val KEY_CURRENCY = "key_currency"
        private const val DEFAULT_CURRENCY = "EUR"
        private const val KEY_CHANGE_TIMELINE = "key_time_interval"
    }
}
