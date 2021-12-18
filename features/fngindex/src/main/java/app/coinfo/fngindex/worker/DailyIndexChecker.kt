package app.coinfo.fngindex.worker

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.coinfo.fngindex.extension.updateFearAndGreedIndexWidgetSmall
import app.coinfo.fngindex.model.FearAndGreedIndex
import app.coinfo.fngindex.repo.Repository
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.FearAndGreedIndexWidgetSmall
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class DailyIndexChecker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    private val widgetManager = AppWidgetManager.getInstance(appContext)

    override suspend fun doWork(): Result {
        Log.d(TAG, "Get Daily Fear and Greed Index")
        return when (val result = repository.getDailyFearAndGreedIndex()) {
            is ResultWrapper.Success -> {
                Log.d(TAG, "   > Success")
                updateAllWidgets(result.data)
                Result.success()
            }
            is ResultWrapper.NetworkError -> {
                Log.d(TAG, "   > Network Error")
                updateAllWidgets(FearAndGreedIndex.FearAndGreedIndexError)
                Result.retry()
            }
            is ResultWrapper.GenericError -> {
                Log.d(TAG, "   > Generic Error")
                updateAllWidgets(FearAndGreedIndex.FearAndGreedIndexError)
                Result.retry()
            }
        }
    }

    private fun updateAllWidgets(data: FearAndGreedIndex) {
        val componentName = ComponentName(appContext, FearAndGreedIndexWidgetSmall::class.java)
        widgetManager.getAppWidgetIds(componentName).forEach { id ->
            widgetManager.updateFearAndGreedIndexWidgetSmall(appContext, id, data)
        }
    }

    companion object {
        private const val TAG = "FNG/DailyIndexChecker"
    }
}
