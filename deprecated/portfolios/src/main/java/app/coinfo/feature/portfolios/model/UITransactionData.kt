package app.coinfo.feature.portfolios.model

data class UITransactionData(
    val transactions: List<UITransaction>,
    val overview: UITransactionOverview,
    val currentPrice: Double
)
