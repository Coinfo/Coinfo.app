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

package app.coinfo.fngindex.net

import app.coinfo.fngindex.net.model.FearAndGreedIndexResponse
import retrofit2.http.GET

/** Service provides access to the Fear and greed Index data */
internal interface AlternativeService {

    @GET("fng/")
    suspend fun requestLatestFearAndGreedIndex(): FearAndGreedIndexResponse
}
