package app.coinfo.library.cloud.model

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval

data class CoinData(
    val name: String,
    val developerInfo: DeveloperInfo?,
    val maxSupply: Double = 0.0,
    val rank: Int,
    val circulatingSupply: Double,
    val totalSupply: Double,
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
