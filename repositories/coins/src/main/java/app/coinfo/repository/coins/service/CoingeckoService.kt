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

package app.coinfo.repository.coins.service

import app.coinfo.repository.coins.service.model.CoinCurrentDataResponse
import app.coinfo.repository.coins.service.model.CoinsList
import app.coinfo.repository.coins.service.model.HistoricalMarketDataResponse
import app.coinfo.repository.coins.service.model.MarketsList
import app.coinfo.repository.coins.service.model.PingResponse
import app.coinfo.repository.coins.service.model.SearchResponse
import app.coinfo.repository.coins.service.model.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CoingeckoService {

    @GET("ping")
    suspend fun ping(): PingResponse

    /** List all supported coins id, name and symbol */
    @GET("coins/list")
    suspend fun coinsList(): CoinsList

    /** Get the current price of any cryptocurrencies in any other supported currencies that you need. */
    @GET("simple/price")
    suspend fun coinPrice(
        @Query("ids") ids: String?,
        @Query("vs_currencies") currencies: String,
    ): Map<String, Map<String, Double>>

    @GET("coins/{id}")
    suspend fun coinInfo(@Path("id") id: String)

    /**
     * List all supported coins price, market cap, volume, and market related data.
     *
     * @param ids - The ids of the coin, comma separated crytocurrency symbols (base)
     * @param targetCurrency - The target currency of market data (usd, eur, jpy, etc.)
     * @param order - sort results by field. Valid values: market_cap_desc, gecko_desc, gecko_asc,
     * market_cap_asc, market_cap_desc, volume_asc, volume_desc, id_asc, id_desc
     * @param perPage - Total results per page, valid values: 1..250
     * @param page - Page through results
     */
    @GET("coins/markets")
    suspend fun coinMarkets(
        @Query("ids") ids: String = "",
        @Query("vs_currency") targetCurrency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("price_change_percentage") priceChangePercentage: String,
    ): MarketsList

    /**
     * Get current data (name, price, market, ... including exchange tickers) for a coin.
     *
     * @param id The coin id (can be obtained from /coins) eg. bitcoin
     * @param localization Include all localized languages in response (true/false) [default: true]
     * @param tickers Include tickers data (true/false) [default: true]
     * @param marketData Include market_data (true/false) [default: true]
     * @param communityData Include community_data data (true/false) [default: true]
     * @param developerData Include developer_data data (true/false) [default: true]
     * @param sparkline Include sparkline 7 days data (eg. true, false) [default: false]
     */
    @GET("coins/{id}")
    suspend fun coinCurrentData(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = true,
        @Query("tickers") tickers: Boolean = true,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = true,
        @Query("developer_data") developerData: Boolean = true,
        @Query("sparkline") sparkline: Boolean = false,
    ): CoinCurrentDataResponse

    /**
     * Get historical market data include price, market cap, and 24h volume (granularity auto)
     *
     * @param id The coin id (can be obtained from /coins) eg. bitcoin
     * @param vsCurrency The target currency of market data (usd, eur, jpy, etc.)
     * @param days Data up to number of days ago (eg. 1,14,30,max)
     * @param interval Data interval. Possible value: daily
     */
    @GET("coins/{id}/market_chart")
    suspend fun historicalMarketData(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: String,
        @Query("interval") interval: String = ""
    ): HistoricalMarketDataResponse

    /**
     * Search for coins, categories and markets listed on CoinGecko ordered by largest Market Cap first
     *
     * @param query - Search string
     */
    @GET("search")
    suspend fun search(
        @Query("query") query: String,
    ): SearchResponse

    /**
     * Top-7 trending coins on CoinGecko as searched by users in the
     * last 24 hours (Ordered by most popular first)
     */
    @GET("search/trending")
    suspend fun trending(): TrendingResponse

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }
}
