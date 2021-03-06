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

package app.coinfo.library.cloud.mapper

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.repository.coins.model.Coin
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.coins.model.DeveloperInfo
import app.coinfo.repository.coins.model.HistoricalMarketData
import app.coinfo.repository.coins.model.PriceDatePair
import app.coinfo.repository.coins.service.model.CoinCurrentDataResponse
import app.coinfo.repository.coins.service.model.HistoricalMarketDataResponse
import app.coinfo.repository.coins.service.model.SearchedCoin
import app.coinfo.repository.coins.service.model.TrendingItem

internal val CoinCurrentDataResponse.asCoinData
    get() = CoinData(
        name = name,
        developerInfo = developerData?.let {
            DeveloperInfo(
                it.forks,
                it.start,
                it.subscribers,
                it.totalIssues,
                it.closedIssues,
                it.pullRequestsMerged
            )
        },
        symbol = symbol,
        rank = marketData.marketCapRank,
        description = descriptions["en"] ?: "",
        circulatingSupply = marketData.circulatingSupply,
        totalSupply = marketData.totalSupply,
        maxSupply = marketData.maxSupply,
        allTimeHighInCurrency = mapOf(
            Currency.USD to (marketData.allTimeHighInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.allTimeHighInCurrency[Currency.EUR.code] ?: 0.0),
            Currency.AMD to (marketData.allTimeHighInCurrency[Currency.AMD.code] ?: 0.0),
            Currency.RUB to (marketData.allTimeHighInCurrency[Currency.RUB.code] ?: 0.0),
        ),
        fullyDilutedValuationInCurrency = mapOf(
            Currency.USD to (marketData.fullyDilutedValuation[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.fullyDilutedValuation[Currency.EUR.code] ?: 0.0),
            Currency.AMD to (marketData.fullyDilutedValuation[Currency.AMD.code] ?: 0.0),
            Currency.RUB to (marketData.fullyDilutedValuation[Currency.RUB.code] ?: 0.0),
        ),
        allTimeLowInCurrency = mapOf(
            Currency.USD to (marketData.allTimeLowInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.allTimeLowInCurrency[Currency.EUR.code] ?: 0.0),
            Currency.AMD to (marketData.allTimeLowInCurrency[Currency.AMD.code] ?: 0.0),
            Currency.RUB to (marketData.allTimeLowInCurrency[Currency.RUB.code] ?: 0.0),
        ),
        marketCapInCurrency = mapOf(
            Currency.USD to (marketData.marketCapInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.marketCapInCurrency[Currency.EUR.code] ?: 0.0),
            Currency.AMD to (marketData.marketCapInCurrency[Currency.AMD.code] ?: 0.0),
            Currency.RUB to (marketData.marketCapInCurrency[Currency.RUB.code] ?: 0.0),
        ),
        currentPrice = mapOf(
            Currency.USD to (marketData.currentPriceInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.currentPriceInCurrency[Currency.EUR.code] ?: 0.0),
            Currency.AMD to (marketData.currentPriceInCurrency[Currency.AMD.code] ?: 0.0),
            Currency.RUB to (marketData.currentPriceInCurrency[Currency.RUB.code] ?: 0.0),
        ),
        percentageChange = mapOf(
            TimeInterval.HOUR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1hInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1hInCurrency[Currency.EUR.code] ?: 0.0),
                Currency.AMD to (marketData.priceChangePercentage1hInCurrency[Currency.AMD.code] ?: 0.0),
                Currency.RUB to (marketData.priceChangePercentage1hInCurrency[Currency.RUB.code] ?: 0.0),
            ),
            TimeInterval.DAY to mapOf(
                Currency.USD to (marketData.priceChangePercentage24hInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage24hInCurrency[Currency.EUR.code] ?: 0.0),
                Currency.AMD to (marketData.priceChangePercentage24hInCurrency[Currency.AMD.code] ?: 0.0),
                Currency.RUB to (marketData.priceChangePercentage24hInCurrency[Currency.RUB.code] ?: 0.0),
            ),
            TimeInterval.WEEK to mapOf(
                Currency.USD to (marketData.priceChangePercentage7dInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage7dInCurrency[Currency.EUR.code] ?: 0.0),
                Currency.AMD to (marketData.priceChangePercentage7dInCurrency[Currency.AMD.code] ?: 0.0),
                Currency.RUB to (marketData.priceChangePercentage7dInCurrency[Currency.RUB.code] ?: 0.0),
            ),
            TimeInterval.MONTH to mapOf(
                Currency.USD to (marketData.priceChangePercentage30dInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage30dInCurrency[Currency.EUR.code] ?: 0.0),
                Currency.AMD to (marketData.priceChangePercentage30dInCurrency[Currency.AMD.code] ?: 0.0),
                Currency.RUB to (marketData.priceChangePercentage30dInCurrency[Currency.RUB.code] ?: 0.0),
            ),
            TimeInterval.YEAR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1yInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1yInCurrency[Currency.EUR.code] ?: 0.0),
                Currency.AMD to (marketData.priceChangePercentage1yInCurrency[Currency.AMD.code] ?: 0.0),
                Currency.RUB to (marketData.priceChangePercentage1yInCurrency[Currency.RUB.code] ?: 0.0),
            ),
        )
    )

internal val HistoricalMarketDataResponse.asHistoricalMarketData
    get() = HistoricalMarketData(
        prices = this.prices.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) },
        marketCaps = this.marketCap.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) }
    )

internal val SearchedCoin.asCoin
    get() = Coin(
        id = id,
        name = name,
        symbol = symbol,
        marketCapRank = marketCapRank,
        image = large
    )

internal val TrendingItem.asCoin
    get() = Coin(
        id = id,
        name = name,
        symbol = symbol,
        marketCapRank = marketCapRank,
        image = imageLarge
    )
