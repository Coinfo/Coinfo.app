package app.coinfo.feature.portfolios.ui.entrypoint

data class UIPortfolioItem(
    val id: Long,
    val name: String,
    val totalValue: String,
    val change24Hour: String,
    val totalProfitLoss: String,
)
