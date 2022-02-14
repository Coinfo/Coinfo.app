package app.coinfo.library.cloud.model

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval

data class CoinData(
    val name: String,
    val developerInfo: DeveloperInfo?,
    private val currentPrice: Map<Currency, Double> = emptyMap(),
    private val percentageChange: Map<TimeInterval, Map<Currency, Double>> = emptyMap(),
) {

    fun getCurrentPrice(currency: Currency) =
        currentPrice[currency] ?: 0.0

    fun getPercentageChange(currency: Currency, timeInterval: TimeInterval) =
        percentageChange[timeInterval]?.get(currency) ?: 0.0
}
