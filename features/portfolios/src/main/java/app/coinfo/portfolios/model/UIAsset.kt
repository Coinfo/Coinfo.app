package app.coinfo.portfolios.model

data class UIAsset(
    val id: String,
    val name: String,
    val price: Double = 0.0,
    val percentage: Float = 0.0f,
    val totalPrice: Double = 0.0,
    val totalHolding: Double = 0.0
) {
    override fun equals(other: Any?) = other?.equals(id) ?: false

    override fun hashCode() = id.hashCode()
}
