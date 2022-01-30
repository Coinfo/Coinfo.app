package app.coinfo.library.cloud.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Double,
    val marketCapRank: Int,
    val priceChangePercentage24h: Double,
)
