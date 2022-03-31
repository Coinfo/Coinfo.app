package app.coinfo.features.portfolio.ui.entrypoint

internal data class UIAssetsItem(
    val id: String,
    val symbol: String,
    val name: String,
    val icon: String,
    val totalHoldings: String,
    val totalPrice: String,
    val totalProfitLoss: String,
    val totalProfitLossPercentage: String,
    val price: String,
    val color: Int,
    val trend: Int,
)
