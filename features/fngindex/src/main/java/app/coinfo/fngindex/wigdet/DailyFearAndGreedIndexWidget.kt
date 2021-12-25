package app.coinfo.fngindex.wigdet

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import app.coinfo.fngindex.wigdet.prefs.Preferences
import app.coinfo.fngindex.wigdet.worker.DailyFearAndGreedIndexWorkerHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
internal class DailyFearAndGreedIndexWidget : AppWidgetProvider() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var workManager: WorkManager

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_UPDATE} and
     * {@link AppWidgetManager#ACTION_APPWIDGET_RESTORED} broadcasts when this AppWidget
     * provider is being asked to provide {@link android.widget.RemoteViews RemoteViews}
     * for a set of AppWidgets.  Override this method to implement your own AppWidget functionality.
     *
     * {@more}
     *
     * @param context   The {@link android.content.Context Context} in which this receiver is
     *                  running.
     * @param appWidgetManager A {@link AppWidgetManager} object you can call {@link
     *                  AppWidgetManager#updateAppWidget} on.
     * @param appWidgetIds The appWidgetIds for which an update is needed.  Note that this
     *                  may be all of the AppWidget instances for this provider, or just
     *                  a subset of them.
     *
     * @see AppWidgetManager#ACTION_APPWIDGET_UPDATE
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d(TAG, "Widget On Update Called")
        Log.d(TAG, "Load Fear And Greed Index Preferences")
        val data = preferences.getDailyFearAndGreedIndex()
        Log.d(TAG, "   > Index            : ${data?.value}")
        Log.d(TAG, "   > Index Name       : ${data?.valueName}")
        Log.d(TAG, "   > Last Update Date : ${data?.lastUpdateDateMillis}")
        Log.d(TAG, "   > Next Update Date : ${data?.nextUpdateDateSeconds}")
        Log.d(TAG, "   > Timestamp        : ${data?.timestampInMillis}")
        Log.d(TAG, "   > Updating Widgets : ${appWidgetIds.map { it.toString() } }}")

        if (data != null) {
            DailyFearAndGreedIndexWidgetHelper.showSuccess(context, appWidgetManager, appWidgetIds, data)
        } else {
            DailyFearAndGreedIndexWidgetHelper.showProgress(context, appWidgetManager, appWidgetIds)
        }
    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_ENABLED} broadcast when
     * the a AppWidget for this provider is instantiated.  Override this method to implement your
     * own AppWidget functionality.
     *
     * {@more}
     * When the last AppWidget for this provider is deleted,
     * {@link AppWidgetManager#ACTION_APPWIDGET_DISABLED} is sent by the AppWidget manager, and
     * {@link #onDisabled} is called.  If after that, an AppWidget for this provider is created
     * again, onEnabled() will be called again.
     *
     * @param context   The {@link android.content.Context Context} in which this receiver is
     *                  running.
     *
     * @see AppWidgetManager#ACTION_APPWIDGET_ENABLED
     */
    override fun onEnabled(context: Context) {
        Log.d(TAG, "Widget On Enabled Called")
        workManager.enqueue(DailyFearAndGreedIndexWorkerHelper.createFearAndGreedIndexCheckWorkRequest())
    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_DISABLED} broadcast, which
     * is sent when the last AppWidget instance for this provider is deleted.  Override this method
     * to implement your own AppWidget functionality.
     *
     * {@more}
     *
     * @param context   The {@link android.content.Context Context} in which this receiver is
     *                  running.
     *
     * @see AppWidgetManager#ACTION_APPWIDGET_DISABLED
     */
    override fun onDisabled(context: Context) {
        Log.d(TAG, "Widget On Disabled:")
        // Cancels all unfinished work with the given tag.  Note that cancellation is a best-effort
        // policy and work that is already executing may continue to run.  Upon cancellation,
        // {@link ListenableWorker#onStopped()} will be invoked for any affected workers.
        workManager.cancelAllWorkByTag(DailyFearAndGreedIndexWorkerHelper.FEAR_AND_GREED_INDEX_CHECK_REQUEST_TAG)

        // Clean all previously saved preferences
        preferences.removeDailyFearAndGreedIndex()
    }

    companion object {
        private const val TAG = "FNG/Widget"
    }
}
