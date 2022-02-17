package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.cloud.model.Coin
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.DEFAULT_DIGITS_AFTER_COMMA
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoinsRepositoryImpl(
    private val cloud: Cloud
) : CoinsRepository {

    override suspend fun loadCoins(
        currency: CurrencyFilterItem,
        timeInterval: TimeInterval
    ): List<CoinListItem> = withContext(Dispatchers.IO) {
        return@withContext cloud.loadCoins(currency.value).map { coin ->
            CoinListItem(
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
