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

internal data class MarketsListItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("current_price")
    val currentPrice: Double,

    @SerializedName("market_cap")
    val marketCap: Double,

    @SerializedName("market_cap_rank")
    val marketCapRank: Int,

    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1h: Double?,

    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24h: Double?,

    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7d: Double?,

    @SerializedName("price_change_percentage_14d_in_currency")
    val priceChangePercentage14d: Double?,

    @SerializedName("price_change_percentage_30d_in_currency")
    val priceChangePercentage30d: Double?,

    @SerializedName("price_change_percentage_200d_in_currency")
    val priceChangePercentage200d: Double?,

    @SerializedName("price_change_percentage_1y_in_currency")
    val priceChangePercentage1y: Double?,
)
