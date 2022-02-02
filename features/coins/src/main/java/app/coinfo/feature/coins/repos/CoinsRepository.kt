package app.coinfo.feature.coins.repos

import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.library.core.utils.Currency

internal interface CoinsRepository {

    /** Load [CoinListItem] for given [Currency] */
    suspend fun loadCoins(
        currency: Currency,
        changeTimeline: ChangeTimelineFilterItem
    ): List<CoinListItem>
}
