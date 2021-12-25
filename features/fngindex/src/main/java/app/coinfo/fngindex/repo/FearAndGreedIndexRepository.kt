package app.coinfo.fngindex.repo

import android.util.Log
import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex
import retrofit2.HttpException
import java.io.IOException

internal class FearAndGreedIndexRepository(
    private val service: AlternativeService,
) : Repository {

    override suspend fun getDailyFearAndGreedIndexForWidget() = try {
        service.requestLatestFearAndGreedIndex().list?.firstOrNull()?.let { data ->
            Log.d(TAG, "Get Daily Fear and Greed Index")
            Log.d(TAG, "   > Value: ${data.value}")
            Log.d(TAG, "   > Value Name: ${data.valueClassification}")
            Log.d(TAG, "   > Last Update Date: ${data.timestamp}")
            Log.d(TAG, "   > Next Update Date: ${data.timeUntilUpdate}")
            val fearAndGreedIndex = DailyFearAndGreedIndex(
                value = data.value.toInt(),
                valueName = data.valueClassification,
                nextUpdateDateSeconds = data.timeUntilUpdate.toInt(),
                lastUpdateDateMillis = data.timestamp.toLong(),
                timestampInMillis = System.currentTimeMillis(),
            )
            ResultWrapper.Success(fearAndGreedIndex)
        } ?: ResultWrapper.GenericError(error = "List doesn't contain fear and greed index data")
    } catch (e: IOException) {
        Log.e(TAG, "Network exception occurs while downloading fear and greed latest index data", e)
        ResultWrapper.NetworkError
    } catch (e: HttpException) {
        Log.e(TAG, "Generic exception occurs while downloading fear and greed latest index data", e)
        ResultWrapper.GenericError(e.code(), e.message())
    }

    companion object {
        private const val TAG = "FNG/Repository"
    }
}
