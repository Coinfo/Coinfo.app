/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.fngindex.wigdet.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import java.util.concurrent.TimeUnit

object DailyFearAndGreedIndexWorkerHelper {

    const val FEAR_AND_GREED_INDEX_CHECK_REQUEST_TAG = "fng_index_check_request_tag"
    private const val BACKOFF_DELAY_IN_MINUTES = 1L

    /**
     * Creates Fear and Greed Index check work request. If no parameters are specified the request
     * will be executed immediately.
     *
     * @param duration The length of the delay in {@code timeUnit} units
     * @param timeUnit The units of time for {@code duration}
     */
    fun createFearAndGreedIndexCheckWorkRequest(duration: Long = 0, timeUnit: TimeUnit = TimeUnit.SECONDS) =
        OneTimeWorkRequestBuilder<DailyFearAndGreedIndexWorker>()
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
            .setBackoffCriteria(BackoffPolicy.LINEAR, BACKOFF_DELAY_IN_MINUTES, TimeUnit.MINUTES)
            .build()
}
