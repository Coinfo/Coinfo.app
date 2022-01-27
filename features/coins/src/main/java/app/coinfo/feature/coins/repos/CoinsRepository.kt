package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem

internal interface CoinsRepository {

    suspend fun loadCoins(): List<CoinListItem>
}
