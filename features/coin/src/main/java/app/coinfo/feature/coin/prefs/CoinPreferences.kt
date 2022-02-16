package app.coinfo.feature.coin.prefs

import android.content.Context
import androidx.core.content.edit
import app.coinfo.library.cloud.enums.TimeInterval
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CoinPreferences @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    private val preferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** Saves selected [TimeInterval] to Shared Preferences. */
    fun saveSelectedTimeInterval(interval: TimeInterval) {
        preferences.edit(commit = true) {
            putInt(KET_TIME_INTERVAL, interval.id)
        }
    }

    /** Loads [TimeInterval] from Shared Preferences, if no value saved before returns [TimeInterval.DAY] */
    fun loadSelectedTimeInterval() = when (preferences.getInt(KET_TIME_INTERVAL, TimeInterval.DAY.id)) {
        TimeInterval.HOUR.id -> TimeInterval.HOUR
        TimeInterval.DAY.id -> TimeInterval.DAY
        TimeInterval.WEEK.id -> TimeInterval.WEEK
        TimeInterval.MONTH.id -> TimeInterval.MONTH
        TimeInterval.TWO_MONTHS.id -> TimeInterval.TWO_MONTHS
        TimeInterval.YEAR.id -> TimeInterval.YEAR
        else -> throw IllegalStateException("Unknown Time Interval value.")
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "coin.prefs"
        private const val KET_TIME_INTERVAL = "key_time_interval"
    }
}
