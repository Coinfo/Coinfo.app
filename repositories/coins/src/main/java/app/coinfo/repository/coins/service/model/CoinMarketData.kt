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

package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class CoinMarketData(
    @SerializedName("current_price")
    val currentPriceInCurrency: Map<String, Double>,
    @SerializedName("ath")
    val allTimeHighInCurrency: Map<String, Double>,
    @SerializedName("atl")
    val allTimeLowInCurrency: Map<String, Double>,
    @SerializedName("ath_change_percentage")
    val allTimeHighChangePercentageInCurrency: Map<String, Double>,
    @SerializedName("ath_date")
    val allTimeHighDateInCurrency: Map<String, String>,
    @SerializedName("market_cap")
    val marketCapInCurrency: Map<String, Double>,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("high_24h")
    val high24hInCurrency: Map<String, Double>,
    @SerializedName("low_24h")
    val low24hInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7dInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_14d_in_currency")
    val priceChangePercentage14dInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_30d_in_currency")
    val priceChangePercentage30dInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_60d_in_currency")
    val priceChangePercentage60dInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_200d_in_currency")
    val priceChangePercentage200dInCurrency: Map<String, Double>,
    @SerializedName("price_change_percentage_1y_in_currency")
    val priceChangePercentage1yInCurrency: Map<String, Double>,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("total_supply")
    val totalSupply: Double,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Map<String, Double>,
)
