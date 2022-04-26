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

package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.UICoinItem
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.DEFAULT_DIGITS_AFTER_COMMA
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoinsInternalRepositoryImpl(
    private val coinsRepository: CoinsRepository
) : CoinsInternalRepository {

    override suspend fun loadCoins(
        ids: List<String>,
        currency: Currency,
        timeInterval: TimeInterval
    ): List<UICoinItem> = withContext(Dispatchers.IO) {
        return@withContext coinsRepository.loadCoins(ids, currency).map { coin ->
            UICoinItem(
                id = coin.id,
                name = coin.name,
                symbol = coin.symbol,
                price = "${coin.currentPrice.toString(DEFAULT_DIGITS_AFTER_COMMA)}${currency.symbol}",
                image = coin.image,
                rank = coin.marketCapRank.toString(),
                priceChangePercentage =
                "${timeInterval.getPriceChangePercentage(coin).toString(DEFAULT_DIGITS_AFTER_COMMA)} %",
                isPriceChangeUp = timeInterval.getPriceChangePercentage(coin) > 0,
                marketCap = "${currency.symbol}${coin.marketCap.toStringWithSuffix(DEFAULT_DIGITS_AFTER_COMMA)}",
            )
        }
    }

    private fun TimeInterval.getPriceChangePercentage(coin: Coin) = when (this) {
        TimeInterval.HOUR -> coin.priceChangePercentage1h
        TimeInterval.DAY -> coin.priceChangePercentage24h
        TimeInterval.WEEK -> coin.priceChangePercentage7d
        TimeInterval.MONTH -> coin.priceChangePercentage30d
        TimeInterval.YEAR -> coin.priceChangePercentage1y
    }
}
