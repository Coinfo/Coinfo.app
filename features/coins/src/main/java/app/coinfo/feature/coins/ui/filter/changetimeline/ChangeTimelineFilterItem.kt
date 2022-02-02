package app.coinfo.feature.coins.ui.filter.changetimeline

import androidx.annotation.StringRes
import app.coinfo.feature.coins.R

enum class ChangeTimelineFilterItem(private val value: String, @StringRes val resId: Int) {
    HOUR("1h", R.string.text_filter_change_timeline_1h),
    DAY("24h", R.string.text_filter_change_timeline_24h),
    WEEK("7d", R.string.text_filter_change_timeline_7d),
    HALF_WEEK("14d", R.string.text_filter_change_timeline_14d),
    MONTH("30d", R.string.text_filter_change_timeline_30d),
    HALF_YEAR("200d", R.string.text_filter_change_timeline_200d),
    YEAR("1y", R.string.text_filter_change_timeline_1y);

    override fun toString() = value
}
