package app.coinfo.portfolios.model

data class UIAsset(
    val id: String,
    val name: String,
    val price: Double,
    val percentage: Float,
    val totalPrice: Double,
    val totalHolding: Double
)
