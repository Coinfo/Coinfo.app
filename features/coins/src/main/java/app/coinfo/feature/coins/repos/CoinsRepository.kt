package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

internal interface CoinsRepository {

    /** Load [CoinListItem] for given [Currency] */
    suspend fun loadCoins(
        currency: CurrencyFilterItem,
        timeInterval: TimeInterval
    ): List<CoinListItem>
}
