package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

internal interface CoinsRepository {

    /** Load [CoinListItem] for given [Currency] */
    suspend fun loadCoins(
        ids: List<String>,
        currency: Currency,
        timeInterval: TimeInterval
    ): List<CoinListItem>
}
