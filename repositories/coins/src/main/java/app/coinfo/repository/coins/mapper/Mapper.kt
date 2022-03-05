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
        ),
        fullyDilutedValuationInCurrency = mapOf(
            Currency.USD to (marketData.fullyDilutedValuation[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.fullyDilutedValuation[Currency.EUR.code] ?: 0.0),
        ),
        allTimeLowInCurrency = mapOf(
            Currency.USD to (marketData.allTimeLowInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.allTimeLowInCurrency[Currency.EUR.code] ?: 0.0),
        ),
        marketCapInCurrency = mapOf(
            Currency.USD to (marketData.marketCapInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.marketCapInCurrency[Currency.EUR.code] ?: 0.0),
        ),
        currentPrice = mapOf(
            Currency.USD to (marketData.currentPriceInCurrency[Currency.USD.code] ?: 0.0),
            Currency.EUR to (marketData.currentPriceInCurrency[Currency.EUR.code] ?: 0.0),
        ),
        percentageChange = mapOf(
            TimeInterval.HOUR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1hInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1hInCurrency[Currency.EUR.code] ?: 0.0),
            ),
            TimeInterval.DAY to mapOf(
                Currency.USD to (marketData.priceChangePercentage24hInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage24hInCurrency[Currency.EUR.code] ?: 0.0),
            ),
            TimeInterval.WEEK to mapOf(
                Currency.USD to (marketData.priceChangePercentage7dInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage7dInCurrency[Currency.EUR.code] ?: 0.0),
            ),
            TimeInterval.MONTH to mapOf(
                Currency.USD to (marketData.priceChangePercentage30dInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage30dInCurrency[Currency.EUR.code] ?: 0.0),
            ),
            TimeInterval.YEAR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1yInCurrency[Currency.USD.code] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1yInCurrency[Currency.EUR.code] ?: 0.0),
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
