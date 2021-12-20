package app.coinfo.fngindex.extension

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import app.coinfo.fngindex.R
import app.coinfo.fngindex.model.FearAndGreedIndex
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val TAG = "FNG/AppWidgetManager"
private const val MAX_FEAR_AND_GREED_VALUE = 100
private const val MAX_COLOR_VALUE = 255

fun AppWidgetManager.updateFearAndGreedIndexWidgetSmall(context: Context, appWidgetId: Int, data: FearAndGreedIndex) {
    Log.d(TAG, "Update Fear and Greed Index Widget Small")
    Log.d(TAG, "   > Value: ${data.value}")
    Log.d(TAG, "   > Value Name: ${data.valueName}")
    Log.d(TAG, "   > Last Update Date: ${data.lastUpdateDateMillis}")
    Log.d(TAG, "   > Next Update Date: ${data.nextUpdateDateSeconds}")
    val views = RemoteViews(context.packageName, R.layout.fear_and_greed_index_widget_small)
    when (data) {
        is FearAndGreedIndex.FearAndGreedIndexSuccess -> {
            views.show(R.id.linear_layout_fng_data)
            views.hide(R.id.text_view_error)
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
            views.hide(R.id.linear_layout_fng_data)
            views.show(R.id.text_view_error)
            views.setTextViewText(
                R.id.text_view_error,
                when (data) {
                    is FearAndGreedIndex.FearAndGreedIndexEmpty -> "Empty"
                    is FearAndGreedIndex.FearAndGreedIndexError -> "Error"
                    else -> "Erro 2"
                }
            )
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
