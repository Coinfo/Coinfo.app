package app.coinfo.library.core.enums

import androidx.annotation.StringRes
import app.coinfo.library.core.R

enum class TimeInterval(
    val uuid: String,
    val numberInDays: Int,
    @StringRes val resId: Int,
) {
    HOUR("95930c70-9022-11ec-b909-0242ac120002", 0, R.string.core_time_interval_hour),
    DAY("95930eb4-9022-11ec-b909-0242ac120002", 1, R.string.core_time_interval_day),
    WEEK("95930fea-9022-11ec-b909-0242ac120002", 7, R.string.core_time_interval_week),
    MONTH("95931242-9022-11ec-b909-0242ac120002", 30, R.string.core_time_interval_month),
    YEAR("95931490-9022-11ec-b909-0242ac120002", 365, R.string.core_time_interval_one_year);

    companion object {
        /** Converts given [uuid] to [TimeInterval], if conversion fails returns [DAY] */
        fun fromUUID(uuid: String?) = values().firstOrNull { it.uuid == uuid } ?: DAY
    }
}
