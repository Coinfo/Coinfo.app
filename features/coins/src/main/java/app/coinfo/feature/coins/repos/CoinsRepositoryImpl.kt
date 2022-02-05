package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.cloud.model.Coin
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
        changeTimeline: ChangeTimelineFilterItem
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
                "${changeTimeline.getPriceChangePercentage(coin).toString(DEFAULT_DIGITS_AFTER_COMMA)} %",
                isPriceChangeUp = changeTimeline.getPriceChangePercentage(coin) > 0,
                marketCap = "${currency.symbol}${coin.marketCap.toStringWithSuffix(DEFAULT_DIGITS_AFTER_COMMA)}",
            )
        }
    }

    private fun ChangeTimelineFilterItem.getPriceChangePercentage(coin: Coin) = when (this) {
        ChangeTimelineFilterItem.HOUR -> coin.priceChangePercentage1h
        ChangeTimelineFilterItem.DAY -> coin.priceChangePercentage24h
        ChangeTimelineFilterItem.WEEK -> coin.priceChangePercentage7d
        ChangeTimelineFilterItem.HALF_WEEK -> coin.priceChangePercentage14d
        ChangeTimelineFilterItem.MONTH -> coin.priceChangePercentage30d
        ChangeTimelineFilterItem.HALF_YEAR -> coin.priceChangePercentage200d
        ChangeTimelineFilterItem.YEAR -> coin.priceChangePercentage1y
    }
}
