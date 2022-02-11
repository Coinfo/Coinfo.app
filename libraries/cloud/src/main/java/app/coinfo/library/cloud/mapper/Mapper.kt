package app.coinfo.library.cloud.mapper

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.HistoricalMarketData
import app.coinfo.library.cloud.model.PriceDatePair
import app.coinfo.library.cloud.service.model.CoinCurrentDataResponse
import app.coinfo.library.cloud.service.model.HistoricalMarketDataResponse

val CoinCurrentDataResponse.asCoin
    get() = CoinData(
        name = name,
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

val HistoricalMarketDataResponse.asHistoricalMarketData
    get() = HistoricalMarketData(
        prices = this.prices.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) },
        marketCaps = this.marketCap.map { data -> PriceDatePair(date = data[0].toLong(), price = data[1]) }
    )
