package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.core.ktx.toString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoinsRepositoryImpl(
    private val cloud: Cloud
) : CoinsRepository {

    override suspend fun loadCoins(): List<CoinListItem> = withContext(Dispatchers.IO) {
        return@withContext cloud.loadCoins().map { coin ->
            CoinListItem(coin.name, coin.currentPrice.toString(2), coin.image)
        }
    }
}
