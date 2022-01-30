package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.core.ktx.DEFAULT_DIGITS_AFTER_COMMA
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.library.core.utils.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoinsRepositoryImpl(
    private val cloud: Cloud
) : CoinsRepository {

    override suspend fun loadCoins(currency: Currency): List<CoinListItem> = withContext(Dispatchers.IO) {
        return@withContext cloud.loadCoins(currency.code).map { coin ->
            CoinListItem(
                name = coin.name,
                symbol = coin.symbol,
                price = "${coin.currentPrice.toString(DEFAULT_DIGITS_AFTER_COMMA)}${currency.symbol}",
                image = coin.image,
                rank = coin.marketCapRank.toString(),
                priceChangePercentage24h = "${coin.priceChangePercentage24h.toString(DEFAULT_DIGITS_AFTER_COMMA)} %",
                isPriceChnage24hUp = coin.priceChangePercentage24h > 0,
                marketCap = "${currency.symbol}${coin.marketCap.toStringWithSuffix(DEFAULT_DIGITS_AFTER_COMMA)}",
            )
        }
    }
}
