package app.coinfo.library.cloud.mapper

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.DeveloperInfo
import app.coinfo.library.cloud.model.HistoricalMarketData
import app.coinfo.library.cloud.model.PriceDatePair
import app.coinfo.library.cloud.service.model.CoinCurrentDataResponse
import app.coinfo.library.cloud.service.model.HistoricalMarketDataResponse

internal val CoinCurrentDataResponse.asCoin
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
        rank = marketData.marketCapRank,
        circulatingSupply = marketData.circulatingSupply,
        totalSupply = marketData.totalSupply,
        maxSupply = marketData.maxSupply,
        allTimeHighInCurrency = mapOf(
            Currency.USD to (marketData.allTimeHighInCurrency[Currency.USD.value] ?: 0.0),
            Currency.EUR to (marketData.allTimeHighInCurrency[Currency.EUR.value] ?: 0.0),
        ),
        fullyDilutedValuationInCurrency = mapOf(
            Currency.USD to (marketData.fullyDilutedValuation[Currency.USD.value] ?: 0.0),
            Currency.EUR to (marketData.fullyDilutedValuation[Currency.EUR.value] ?: 0.0),
        ),
        allTimeLowInCurrency = mapOf(
            Currency.USD to (marketData.allTimeLowInCurrency[Currency.USD.value] ?: 0.0),
            Currency.EUR to (marketData.allTimeLowInCurrency[Currency.EUR.value] ?: 0.0),
        ),
        marketCapInCurrency = mapOf(
            Currency.USD to (marketData.marketCapInCurrency[Currency.USD.value] ?: 0.0),
            Currency.EUR to (marketData.marketCapInCurrency[Currency.EUR.value] ?: 0.0),
        ),
        currentPrice = mapOf(
            Currency.USD to (marketData.currentPriceInCurrency[Currency.USD.value] ?: 0.0),
            Currency.EUR to (marketData.currentPriceInCurrency[Currency.EUR.value] ?: 0.0),
        ),
        percentageChange = mapOf(
            TimeInterval.HOUR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1hInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1hInCurrency[Currency.EUR.value] ?: 0.0),
            ),
            TimeInterval.DAY to mapOf(
                Currency.USD to (marketData.priceChangePercentage24hInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage24hInCurrency[Currency.EUR.value] ?: 0.0),
            ),
            TimeInterval.WEEK to mapOf(
                Currency.USD to (marketData.priceChangePercentage7dInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage7dInCurrency[Currency.EUR.value] ?: 0.0),
            ),
            TimeInterval.MONTH to mapOf(
                Currency.USD to (marketData.priceChangePercentage30dInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage30dInCurrency[Currency.EUR.value] ?: 0.0),
            ),
            TimeInterval.TWO_MONTHS to mapOf(
                Currency.USD to (marketData.priceChangePercentage60dInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage60dInCurrency[Currency.EUR.value] ?: 0.0),
            ),
            TimeInterval.YEAR to mapOf(
                Currency.USD to (marketData.priceChangePercentage1yInCurrency[Currency.USD.value] ?: 0.0),
                Currency.EUR to (marketData.priceChangePercentage1yInCurrency[Currency.EUR.value] ?: 0.0),
            ),
        )
    )

internal val HistoricalMarketDataResponse.asHistoricalMarketData
    get() = HistoricalMarketData(
        prices = this.prices.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) },
        marketCaps = this.marketCap.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) }
    )
