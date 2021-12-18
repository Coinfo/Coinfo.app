package app.coinfo.fngindex.extension

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import app.coinfo.fngindex.R
import app.coinfo.fngindex.model.FearAndGreedIndex

private const val TAG = "FNG/AppWidgetManager"

fun AppWidgetManager.updateFearAndGreedIndexWidgetSmall(context: Context, appWidgetId: Int, data: FearAndGreedIndex) {
    Log.d(TAG, "Update Fear and Greed Index Widget Small")
    Log.d(TAG, "   > Value: ${data.value}")
    Log.d(TAG, "   > Value Name: ${data.valueName}")
    Log.d(TAG, "   > Last Update Date: ${data.lastUpdateDateMillis}")
    Log.d(TAG, "   > Next Update Date: ${data.nextUpdateDateSeconds}")
    val views = RemoteViews(context.packageName, R.layout.fear_and_greed_index_widget_small)
    when (data) {
        is FearAndGreedIndex.FearAndGreedIndexSuccess -> {
            views.show(R.id.text_index_value)
            views.show(R.id.text_index_value_name)
            views.show(R.id.label_index)
            views.hide(R.id.text_view_error)
            views.setTextViewText(R.id.text_index_value, data.value)
            views.setTextViewText(R.id.text_index_value_name, data.valueName)
            views.setTextViewText(R.id.text_view_next_update_date, "${data.nextUpdateDateSeconds}")
        }
        else -> {
            views.hide(R.id.text_index_value)
            views.hide(R.id.text_index_value_name)
            views.hide(R.id.label_index)
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
