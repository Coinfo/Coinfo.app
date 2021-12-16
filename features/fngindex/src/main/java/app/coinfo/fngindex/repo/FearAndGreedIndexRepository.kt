package app.coinfo.fngindex.repo

import android.content.Context
import androidx.work.*
import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.net.model.FearAndGreedIndexResponse
import app.coinfo.fngindex.model.FearAndGreedIndex
import app.coinfo.fngindex.prefs.Preferences
import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.FearAndGreedIndexWidgetSmall
import app.coinfo.fngindex.worker.DailyIndexChecker
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

internal class FearAndGreedIndexRepository(
    private val service: AlternativeService,
    private val preferences: Preferences,
    private val appContext: Context
) : Repository {

    override suspend fun getDailyFearAndGreedIndex() = try {
        val fearAndGreedIndex = service.requestLatestFearAndGreedIndex().asFearAndGreedIndex
        preferences.setFearAndGreedIndex(fearAndGreedIndex)
        scheduleNextWork()
        ResultWrapper.Success(fearAndGreedIndex)
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                val code = throwable.code()
                //val errorResponse = convertErrorBody(throwable)
                ResultWrapper.GenericError(code, "")
            }
            else -> ResultWrapper.GenericError(null, null)
        }
    }

    private fun scheduleNextWork() {
        val checkFearAndGreedIndex = OneTimeWorkRequestBuilder<DailyIndexChecker>()
            // Adds a tag for the work. You can query and cancel work by tags. Tags are particularly
            // useful for modules or libraries to find and operate on their own work.
            .addTag("fng_work_tag")
            .setInitialDelay(30, TimeUnit.SECONDS)
            // Adds constraints
            .setConstraints(
                Constraints.Builder()
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
        WorkManager.getInstance(appContext).enqueue(checkFearAndGreedIndex)
    }

    private val FearAndGreedIndexResponse.asFearAndGreedIndex
        get() = this.list?.first()?.let {
            FearAndGreedIndex(
                value = it.value,
                valueName = it.valueClassification,
                lastUpdateDateMillis = it.timestamp.toLong(),
                nextUpdateDateSeconds = it.timeUntilUpdate.toLong()
            )
        } ?: throw IllegalArgumentException("Something went wrong while reading data from response.")

    companion object {
        private const val TAG = "FNG/Repository"
    }
}
