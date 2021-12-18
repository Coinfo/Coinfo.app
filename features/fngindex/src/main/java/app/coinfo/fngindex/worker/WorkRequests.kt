package app.coinfo.fngindex.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import java.util.concurrent.TimeUnit

object WorkRequests {

    const val FEAR_AND_GREED_INDEX_CHECK_REQUEST_TAG = "fng_index_check_request_tag"

    /**
     * Creates Fear and Greed Index check work request. If no parameters are specified the request
     * will be executed immediately.
     *
     * @param duration The length of the delay in {@code timeUnit} units
     * @param timeUnit The units of time for {@code duration}
     */
    fun createFearAndGreedIndexCheckWorkRequest(duration: Long = 0, timeUnit: TimeUnit = TimeUnit.SECONDS) =
        OneTimeWorkRequestBuilder<DailyIndexChecker>()
            // Adds a tag for the work. You can query and cancel work by tags. Tags are particularly
            // useful for modules or libraries to find and operate on their own work.
            .addTag(FEAR_AND_GREED_INDEX_CHECK_REQUEST_TAG)
            .setInitialDelay(duration, timeUnit)
            // Adds constraints
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(false)
                    .setRequiresBatteryNotLow(false)
                    .build()
            )
            // Sets the backoff policy and backoff delay for the work.
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
}
