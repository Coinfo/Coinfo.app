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

package app.coinfo.repository.coins.model

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

data class CoinData(
    val name: String,
    val symbol: String,
    val developerInfo: DeveloperInfo?,
    val maxSupply: Double = 0.0,
    val rank: Int,
    val circulatingSupply: Double,
    val totalSupply: Double,
    val description: String,
    private val currentPrice: Map<Currency, Double> = emptyMap(),
    private val percentageChange: Map<TimeInterval, Map<Currency, Double>> = emptyMap(),
    private val marketCapInCurrency: Map<Currency, Double> = emptyMap(),
    private val allTimeHighInCurrency: Map<Currency, Double> = emptyMap(),
    private val allTimeLowInCurrency: Map<Currency, Double> = emptyMap(),
    private val fullyDilutedValuationInCurrency: Map<Currency, Double> = emptyMap(),
) {

    fun getCurrentPrice(currency: Currency) =
        currentPrice[currency] ?: 0.0

    fun getPercentageChange(currency: Currency, timeInterval: TimeInterval) =
        percentageChange[timeInterval]?.get(currency) ?: 0.0

    fun getMarketCap(currency: Currency) = marketCapInCurrency[currency] ?: 0.0

    fun getAllTimeHigh(currency: Currency) = allTimeHighInCurrency[currency] ?: 0.0

    fun getAllTimeLow(currency: Currency) = allTimeLowInCurrency[currency] ?: 0.0

    fun getFullyDilutedValuation(currency: Currency) = fullyDilutedValuationInCurrency[currency] ?: 0.0
}
