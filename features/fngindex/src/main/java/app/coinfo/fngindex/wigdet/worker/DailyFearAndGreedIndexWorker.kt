package app.coinfo.fngindex.wigdet.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await
import app.coinfo.fngindex.repo.Repository
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.DailyFearAndGreedIndexWidgetHelper
import app.coinfo.fngindex.wigdet.prefs.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
/** A worker which download Fear and Greed Index. */
internal class DailyFearAndGreedIndexWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: Repository,
    private val workManager: WorkManager,
    private val preferences: Preferences,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork() = when (val result = repository.getDailyFearAndGreedIndexForWidget()) {
        is ResultWrapper.Success -> {
            preferences.setDailyFearAndGreedIndex(result.data)
            scheduleNextUpdateOnSuccess(result.data.nextUpdateDateSeconds)
            DailyFearAndGreedIndexWidgetHelper.showSuccess(context = appContext, data = result.data)
            Result.success()
        }
        is ResultWrapper.NetworkError -> {
            DailyFearAndGreedIndexWidgetHelper.showError(appContext)
            Result.retry()
        }
        is ResultWrapper.GenericError -> {
            DailyFearAndGreedIndexWidgetHelper.showError(appContext)
            Result.retry()
        }
    }

    private suspend fun scheduleNextUpdateOnSuccess(nextUpdateInSeconds: Int) {
        Log.d(TAG, "Scheduling next update in $nextUpdateInSeconds seconds.")
        val state: Operation.State = workManager.enqueue(
            DailyFearAndGreedIndexWorkerHelper.createFearAndGreedIndexCheckWorkRequest(
                nextUpdateInSeconds + ADDITIONAL_WAIT_TIME_IN_SECONDS
            )
        ).await()
        when (state) {
            is Operation.State.IN_PROGRESS ->
                Log.d(TAG, "  > State status: Progress")
            is Operation.State.SUCCESS ->
                Log.d(TAG, "  > State status: Success")
            is Operation.State.FAILURE -> {
                Log.e(TAG, "  > State status: Failure")
                // If the schedule failed, try to reschedule,
                // so that next time fear and greed index is updated.
                // Will this ever happen?
                workManager.enqueue(
                    DailyFearAndGreedIndexWorkerHelper.createFearAndGreedIndexCheckWorkRequest(
                        nextUpdateInSeconds + ADDITIONAL_WAIT_TIME_IN_SECONDS
                    )
                )
            }
        }
    }

    companion object {
        private const val TAG = "FNG/WidgetWorker"

        // Additional 5 minutes of waiting time before next update check.
        // Needs this time to be sure that on the server side data updated.
        private const val ADDITIONAL_WAIT_TIME_IN_SECONDS = 300L
    }
}
