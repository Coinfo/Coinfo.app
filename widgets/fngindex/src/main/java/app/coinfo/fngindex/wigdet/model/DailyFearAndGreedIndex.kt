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

package app.coinfo.fngindex.wigdet.model

data class DailyFearAndGreedIndex(
    /** Value name of daily fear and greed index. */
    val valueName: String,
    /** Value of daily fear and greed index. */
    val value: Int,
    /** Time when daily fear and greed index was last time updated. */
    val lastUpdateDateMillis: Long,
    /**
     * Time in seconds when next update will happen, this time should be calculated
     * starting from [timestampInMillis].
     */
    val nextUpdateDateSeconds: Int,
    /** Time when daily fear and greed index object was created. */
    val timestampInMillis: Long,
)
