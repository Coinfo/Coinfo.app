package app.coinfo.library.cloud.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Double,
    val marketCapRank: Int,
    val priceChangePercentage1h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val priceChangePercentage14d: Double,
    val priceChangePercentage30d: Double,
    val priceChangePercentage200d: Double,
    val priceChangePercentage1y: Double,
)
