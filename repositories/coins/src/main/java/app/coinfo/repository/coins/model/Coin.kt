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

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double = 0.0,
    val marketCap: Double = 0.0,
    val marketCapRank: Int,
    val priceChangePercentage1h: Double = 0.0,
    val priceChangePercentage24h: Double = 0.0,
    val priceChangePercentage7d: Double = 0.0,
    val priceChangePercentage30d: Double = 0.0,
    val priceChangePercentage1y: Double = 0.0,
)
