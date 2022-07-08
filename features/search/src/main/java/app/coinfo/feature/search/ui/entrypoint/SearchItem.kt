package app.coinfo.feature.search.ui.entrypoint

/** Interface for Search item represented in the UI */
internal interface UISearchItem {
    val id: String
    val name: String
    val symbol: String
    val rank: String
    val image: String
}

internal data class UISearchResultItem(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val rank: String,
    override val image: String
) : UISearchItem

internal data class UITrendingResultItem(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val rank: String,
    override val image: String
) : UISearchItem
