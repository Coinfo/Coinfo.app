package app.coinfo.feature.coin.prefs

import android.content.Context
import androidx.core.content.edit
import app.coinfo.library.core.enums.TimeInterval
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/** Shared Preferences used to save ONLY data which is used locally by feature coin. */
@Singleton
internal class CoinPreferences @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    private val preferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** Saves selected [TimeInterval] to Shared Preferences. */
    fun saveSelectedTimeInterval(interval: TimeInterval) {
        preferences.edit(commit = true) {
            putString(KET_TIME_INTERVAL, interval.uuid)
        }
    }

    /** Loads [TimeInterval] from Shared Preferences, if no value saved before returns [TimeInterval.DAY] */
    fun loadSelectedTimeInterval() = when (preferences.getString(KET_TIME_INTERVAL, TimeInterval.DAY.uuid)) {
        TimeInterval.HOUR.uuid -> TimeInterval.HOUR
        TimeInterval.DAY.uuid -> TimeInterval.DAY
        TimeInterval.WEEK.uuid -> TimeInterval.WEEK
        TimeInterval.MONTH.uuid -> TimeInterval.MONTH
        TimeInterval.TWO_MONTHS.uuid -> TimeInterval.TWO_MONTHS
        TimeInterval.YEAR.uuid -> TimeInterval.YEAR
        else -> throw IllegalStateException("Unknown Time Interval value.")
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "coin.prefs"
        private const val KET_TIME_INTERVAL = "key_time_interval"
    }
}
