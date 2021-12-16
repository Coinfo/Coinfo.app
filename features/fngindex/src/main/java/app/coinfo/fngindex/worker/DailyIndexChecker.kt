package app.coinfo.fngindex.worker

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.coinfo.fngindex.repo.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import android.content.ComponentName
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.FearAndGreedIndexWidgetSmall
import app.coinfo.fngindex.wigdet.updateAppWidget


@HiltWorker
internal class DailyIndexChecker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    private val widgetManager = AppWidgetManager.getInstance(appContext)

    override suspend fun doWork(): Result {
        Log.d(TAG, "Get Daily Fear and Greed Index")
        val result = repository.getDailyFearAndGreedIndex()
        return when (result) {
            is ResultWrapper.Success -> {
                Log.d(TAG, "   > Success")
                widgetManager.getAppWidgetIds(ComponentName(appContext, FearAndGreedIndexWidgetSmall::class.java)).forEach {
                    updateAppWidget(
                        appContext,
                        AppWidgetManager.getInstance(appContext),
                        it,
                        result.value
                    )
                }
                Result.success()
            }
            is ResultWrapper.NetworkError -> {
                Log.d(TAG, "   > Network Error")
                Result.retry()
            }
            is ResultWrapper.GenericError -> {
                Log.d(TAG, "   > Generic Error")
                Result.retry()
            }
        }
    }


    companion object {
        private const val TAG = "FNG/DailyIndexChecker"
    }
}
