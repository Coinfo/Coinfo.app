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

@HiltWorker
internal class DailyIndexChecker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    private val widgetManager = AppWidgetManager.getInstance(appContext)

    override suspend fun doWork(): Result {
        Log.d(TAG, "Get Daily Fear and Greed Index")
        val index = repository.getDailyFearAndGreedIndex()
        Log.d(TAG, "$index")

        return Result.success()
    }

    private fun updateWidget() {

    }

    companion object {
        private const val TAG = "FNG/DailyIndexChecker"
    }
}
