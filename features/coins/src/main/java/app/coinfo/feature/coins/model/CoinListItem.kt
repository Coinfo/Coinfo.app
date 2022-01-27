package app.coinfo.feature.coins.model

import app.coinfo.feature.coins.ui.entrypoint.CoinsAdapter

/** Coin item which is displayed inside [CoinsAdapter] */
internal data class CoinListItem(
    val name: String,
    val price: String,
    val image: String,
)
