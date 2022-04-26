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

package app.coinfo.repository.coins

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.repository.coins.model.Coin
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.coins.model.HistoricalMarketData
import app.coinfo.repository.coins.model.SearchResults
import app.coinfo.repository.coins.model.ServerStatus
import app.coinfo.repository.coins.model.TrendingResults

interface CoinsRepository {

    /** Returns true if server is up and running; otherwise false. */
    suspend fun getServerStatus(): ServerStatus

    /** Returns list of coins */
    suspend fun getCoinInfo(symbol: String)

    /** Returns Coin Price in Euro */
    suspend fun getCoinPrice(symbol: String): Double

    suspend fun loadCoins(ids: List<String>, currency: Currency): List<Coin>

    /** Returns [CoinData] for the given coin [id]. */
    suspend fun getCoinData(id: String): CoinData

    suspend fun getCoinHistoricalMarketData(
        id: String,
        currency: Currency,
        timeInterval: TimeInterval,
    ): HistoricalMarketData

    suspend fun search(query: String): SearchResults

    suspend fun getTrending(): TrendingResults
}
