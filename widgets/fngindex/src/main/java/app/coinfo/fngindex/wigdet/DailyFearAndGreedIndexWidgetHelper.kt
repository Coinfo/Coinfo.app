/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.fngindex.wigdet

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import app.coinfo.fngindex.R
import app.coinfo.fngindex.wigdet.ext.hide
import app.coinfo.fngindex.wigdet.ext.show
import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DailyFearAndGreedIndexWidgetHelper {

    /** Maximum value which can has gear and greed index */
    private const val MAX_FEAR_AND_GREED_VALUE = 100
    private const val MAX_COLOR_VALUE = 255

    /** Renders Daily fear and greed index data on the widget. */
    fun showSuccess(
        context: Context,
        widgetManager: AppWidgetManager = AppWidgetManager.getInstance(context),
        widgetIds: IntArray =
            widgetManager.getAppWidgetIds(ComponentName(context, DailyFearAndGreedIndexWidget::class.java)),
        data: DailyFearAndGreedIndex,
    ) {
        // Update widget UI with provided data
        widgetIds.forEach { id ->
            val views = RemoteViews(context.packageName, R.layout.daily_fear_and_greed_index_widget)
            views.show(R.id.linear_layout_fng_data)
            views.hide(R.id.linear_layout_fng_error)
            views.setTextViewText(R.id.text_index_value, data.value.toString())
            views.setTextViewText(R.id.text_index_value_name, data.valueName)
            views.setTextColor(R.id.text_index_value_name, getFearAndGreedColor(data.value))
            views.setTextColor(R.id.text_index_value, getFearAndGreedColor(data.value))
            views.setTextViewText(
                R.id.text_view_next_update_date,
                context.getString(
                    R.string.appwidget_text_next_update,
                    getNextUpdateDate(data.nextUpdateDateSeconds, data.timestampInMillis)
                )
            )
            widgetManager.updateAppWidget(id, views)
        }
    }

    /** Renders Progress on the widget */
    fun showProgress(
        context: Context,
        widgetManager: AppWidgetManager = AppWidgetManager.getInstance(context),
        widgetIds: IntArray =
            widgetManager.getAppWidgetIds(ComponentName(context, DailyFearAndGreedIndexWidget::class.java)),
    ) {
        widgetIds.forEach { id ->
            val views = RemoteViews(context.packageName, R.layout.daily_fear_and_greed_index_widget)
            views.hide(R.id.linear_layout_fng_data)
            views.show(R.id.linear_layout_fng_error)
            views.setTextViewText(R.id.text_view_error, context.getString(R.string.appwidget_text_loading_data))
            views.show(R.id.progress_bar)
            widgetManager.updateAppWidget(id, views)
        }
    }

    /** Renders Error on the widget */
    fun showError(
        context: Context,
        widgetManager: AppWidgetManager = AppWidgetManager.getInstance(context),
        widgetIds: IntArray =
            widgetManager.getAppWidgetIds(ComponentName(context, DailyFearAndGreedIndexWidget::class.java)),
    ) {
        widgetIds.forEach { id ->
            val views = RemoteViews(context.packageName, R.layout.daily_fear_and_greed_index_widget)
            views.hide(R.id.linear_layout_fng_data)
            views.show(R.id.linear_layout_fng_error)
            views.setTextViewText(R.id.text_view_error, context.getString(R.string.appwidget_error_failed_to_update))
            views.hide(R.id.progress_bar)
            widgetManager.updateAppWidget(id, views)
        }
    }

    private fun getNextUpdateDate(seconds: Int, timestamp: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.SECOND, seconds)
        return SimpleDateFormat("d MMM HH:mm", Locale.getDefault()).format(calendar.time)
    }

    private fun getFearAndGreedColor(value: Int) = Color.rgb(
        (MAX_COLOR_VALUE * (MAX_FEAR_AND_GREED_VALUE - value)) / MAX_FEAR_AND_GREED_VALUE,
        (MAX_COLOR_VALUE * value) / MAX_FEAR_AND_GREED_VALUE,
        0
    )
}
