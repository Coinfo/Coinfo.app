package app.coinfo.fngindex.repo

import android.util.Log
import androidx.work.WorkManager
import app.coinfo.fngindex.model.FearAndGreedIndex
import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.net.model.FearAndGreedIndexResponse
import app.coinfo.fngindex.prefs.Preferences
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.worker.WorkRequests
import retrofit2.HttpException
import java.io.IOException

internal class FearAndGreedIndexRepository(
    private val service: AlternativeService,
    private val preferences: Preferences,
    private val workManager: WorkManager
) : Repository {

    override suspend fun getDailyFearAndGreedIndex() = try {
        val data = service.requestLatestFearAndGreedIndex().asFearAndGreedIndex
        Log.d(TAG, "Get Daily Fear and Greed Index")
        Log.d(TAG, "   > Value: ${data.value}")
        Log.d(TAG, "   > Value Name: ${data.valueName}")
        Log.d(TAG, "   > Last Update Date: ${data.lastUpdateDateMillis}")
        Log.d(TAG, "   > Next Update Date: ${data.nextUpdateDateSeconds}")
        preferences.setFearAndGreedIndex(data)
        workManager.enqueue(
            WorkRequests.createFearAndGreedIndexCheckWorkRequest(
                data.nextUpdateDateSeconds + ADDITIONAL_WAIT_TIME_IN_SECONDS
            )
        )
        ResultWrapper.Success(data)
    } catch (e: IOException) {
        Log.e(TAG, "Exception occurs, while downloading fear and greed index", e)
        ResultWrapper.NetworkError
    } catch (e: HttpException) {
        Log.e(TAG, "Exception occurs, while downloading fear and greed index", e)
        ResultWrapper.GenericError(e.code(), e.message())
    } catch (e: IllegalStateException) {
        Log.e(TAG, "Exception occurs, while downloading fear and greed index", e)
        ResultWrapper.GenericError()
    }

    private val FearAndGreedIndexResponse.asFearAndGreedIndex
        get() = this.list?.first()?.let {
            FearAndGreedIndex.FearAndGreedIndexSuccess(
                value = it.value,
                valueName = it.valueClassification,
                lastUpdateDateMillis = it.timestamp.toLong(),
                nextUpdateDateSeconds = it.timeUntilUpdate.toLong()
            )
        } ?: throw IllegalStateException("Unable to get data for first item in the fear and greed list")

    companion object {
        private const val TAG = "FNG/Repository"

        // Additional 5 minutes of waiting time before next update check.
        // Needs this time to be sure that on the server side data updated.
        private const val ADDITIONAL_WAIT_TIME_IN_SECONDS = 300L
    }
}
