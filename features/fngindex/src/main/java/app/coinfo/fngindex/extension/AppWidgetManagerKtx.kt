package app.coinfo.fngindex.extension

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import app.coinfo.fngindex.R
import app.coinfo.fngindex.model.FearAndGreedIndex
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val MAX_FEAR_AND_GREED_VALUE = 100
private const val MAX_COLOR_VALUE = 255

fun AppWidgetManager.updateFearAndGreedIndexWidgetSmall(context: Context, appWidgetId: Int, data: FearAndGreedIndex) {
    val views = RemoteViews(context.packageName, R.layout.fear_and_greed_index_widget_small)
    when (data) {
        is FearAndGreedIndex.FearAndGreedIndexSuccess -> {
            views.show(R.id.linear_layout_fng_data)
            views.hide(R.id.linear_layout_fng_error)
            views.setTextViewText(R.id.text_index_value, data.value)
            views.setTextViewText(R.id.text_index_value_name, data.valueName)
            views.setTextColor(R.id.text_index_value_name, getFearAndGreedColor(data.value.toInt()))
            views.setTextColor(R.id.text_index_value, getFearAndGreedColor(data.value.toInt()))
            views.setTextViewText(
                R.id.text_view_next_update_date,
                context.getString(R.string.appwidget_text_next_update, getNextUpdateDate(data.nextUpdateDateSeconds))
            )
        }
        else -> {
            val error = context.getString(R.string.appwidget_error_failed_to_update)
            views.hide(R.id.linear_layout_fng_data)
            views.show(R.id.linear_layout_fng_error)
            when (data) {
                is FearAndGreedIndex.FearAndGreedIndexEmpty -> {
                    views.setTextViewText(R.id.text_view_error, context.getString(R.string.appwidget_text_loading_data))
                    views.show(R.id.progress_bar)
                }
                is FearAndGreedIndex.FearAndGreedIndexError -> {
                    views.setTextViewText(R.id.text_view_error, error)
                    views.hide(R.id.progress_bar)
                }
                else -> {
                    views.setTextViewText(R.id.text_view_error, error)
                    views.hide(R.id.progress_bar)
                }
            }
        }
    }
    updateAppWidget(appWidgetId, views)
}

/** Adds given remaining [seconds] to current date and returns update date formatted as d MMM HH:mm */
private fun getNextUpdateDate(seconds: Long): String {
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.SECOND, seconds.toInt())
    return SimpleDateFormat("d MMM HH:mm", Locale.getDefault()).format(calendar.time)
}

private fun getFearAndGreedColor(value: Int) = Color.rgb(
    (MAX_COLOR_VALUE * (MAX_FEAR_AND_GREED_VALUE - value)) / MAX_FEAR_AND_GREED_VALUE,
    (MAX_COLOR_VALUE * value) / MAX_FEAR_AND_GREED_VALUE,
    0
)
