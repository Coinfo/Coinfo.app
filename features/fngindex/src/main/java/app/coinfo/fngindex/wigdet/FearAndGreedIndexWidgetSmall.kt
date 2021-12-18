package app.coinfo.fngindex.wigdet

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import app.coinfo.fngindex.extension.updateFearAndGreedIndexWidgetSmall
import app.coinfo.fngindex.prefs.Preferences
import app.coinfo.fngindex.worker.WorkRequests
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
internal class FearAndGreedIndexWidgetSmall : AppWidgetProvider() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var workManager: WorkManager

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
        Log.d(TAG, "Widget On Update Called:")
        Log.d(TAG, "Load Fear And Greed Index Preferences")

        val data = preferences.getFearAndGreedIndex()

        appWidgetIds.forEach { id ->
            appWidgetManager.updateFearAndGreedIndexWidgetSmall(context, id, data)
        }
    }

    /**
     * This is called when an instance the widget is created for the first time.
     * For example, if the user adds two instances of your widget, this is only called the first time.
     * If you need to open a new database or perform another setup that only needs to occur once for
     * all widget instances, then this is a good place to do it.
     */
    override fun onEnabled(context: Context) {
        Log.d(TAG, "Widget On Enabled Called")
        workManager.enqueue(WorkRequests.createFearAndGreedIndexCheckWorkRequest())
    }

    /**
     * This is called when the last instance of your widget is deleted from the widget host.
     * This is where you should clean up any work done in onEnabled(Context), such as delete a temporary database.
     */
    override fun onDisabled(context: Context) {
        Log.d(TAG, "Widget On Disabled:")
        // Cancels all unfinished work with the given tag.  Note that cancellation is a best-effort
        // policy and work that is already executing may continue to run.  Upon cancellation,
        // {@link ListenableWorker#onStopped()} will be invoked for any affected workers.
        workManager.cancelAllWorkByTag(WorkRequests.FEAR_AND_GREED_INDEX_CHECK_REQUEST_TAG)

        // Clean all previously saved preferences
        preferences.reset()
    }

    companion object {
        private const val TAG = "FNG/IndexWidgetSmall"
    }
}
