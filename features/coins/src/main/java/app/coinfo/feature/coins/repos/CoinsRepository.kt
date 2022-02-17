package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.core.enums.Currency

internal interface CoinsRepository {

    /** Load [CoinListItem] for given [Currency] */
    suspend fun loadCoins(
        currency: CurrencyFilterItem,
        changeTimeline: ChangeTimelineFilterItem
    ): List<CoinListItem>
}
