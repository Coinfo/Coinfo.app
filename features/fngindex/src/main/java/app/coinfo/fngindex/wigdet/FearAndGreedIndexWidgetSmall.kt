package app.coinfo.fngindex.wigdet

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.work.*
import app.coinfo.fngindex.R
import app.coinfo.fngindex.repo.Repository
import app.coinfo.fngindex.worker.DailyIndexChecker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
internal class FearAndGreedIndexWidgetSmall : AppWidgetProvider() {

    /**
     * This is called to update the widget at intervals defined by the updatePeriodMillis attribute
     * in the AppWidgetProviderInfo. (See the table describing additional widget attributes in this document).
     *
     * This method is also called when the user adds the widget, so it should perform the essential setup,
     * such as define event handlers for View objects or start a job to load data to be displayed in the widget.
     * However, if you have declared a configuration activity without the configuration_optional flag, this
     * method is not called when the user adds the widget, but is called for the subsequent updates.
     *
     * It is the responsibility of the configuration activity to perform the first update when
     * configuration is complete. (See Creating a widget configuration activity.)
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d(TAG, "onUpdate() called for: First Fear and Greed Index Widget Small")
    }

    /**
     * This is called when an instance the widget is created for the first time.
     * For example, if the user adds two instances of your widget, this is only called the first time.
     * If you need to open a new database or perform another setup that only needs to occur once for
     * all widget instances, then this is a good place to do it.
     */
    override fun onEnabled(context: Context) {
        Log.d(TAG, "onEnabled() called for: First Fear and Greed Index Widget Small")
        val checkFearAndGreedIndex: WorkRequest =
            OneTimeWorkRequestBuilder<DailyIndexChecker>()
                // Adds a tag for the work. You can query and cancel work by tags. Tags are particularly
                // useful for modules or libraries to find and operate on their own work.
                .addTag(WORK_TAG)
                // Adds constraints
                .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(false)
                    .setRequiresBatteryNotLow(false)
                    .build())
                // Sets the backoff policy and backoff delay for the work.
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS)
                .build()
        WorkManager.getInstance(context).enqueue(checkFearAndGreedIndex)
    }

    /**
     * This is called when the last instance of your widget is deleted from the widget host.
     * This is where you should clean up any work done in onEnabled(Context), such as delete a temporary database.
     */
    override fun onDisabled(context: Context) {
        Log.d(TAG, "onDisabled() called for: Last Fear and Greed Index Widget Small")
        // Cancels all unfinished work with the given tag.  Note that cancellation is a best-effort
        // policy and work that is already executing may continue to run.  Upon cancellation,
        // {@link ListenableWorker#onStopped()} will be invoked for any affected workers.
        WorkManager.getInstance(context).cancelAllWorkByTag(WORK_TAG)
    }

    companion object {
        private const val TAG = "FNG/IndexWidgetSmall"
        private const val WORK_TAG = "fng_work_tag"
    }
}

//internal fun updateAppWidget(
//    context: Context,
//    appWidgetManager: AppWidgetManager,
//    appWidgetId: Int
//) {
//    val widgetText = context.getString(R.string.appwidget_text)
//    // Construct the RemoteViews object
//    val views = RemoteViews(context.packageName, R.layout.fear_and_greed_index_widget_small)
//    //views.setTextViewText(R.id.appwidget_text, widgetText)
//
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)
//}
