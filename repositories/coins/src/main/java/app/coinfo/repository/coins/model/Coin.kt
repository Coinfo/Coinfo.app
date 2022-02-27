package app.coinfo.repository.coins.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double = 0.0,
    val marketCap: Double = 0.0,
    val marketCapRank: Int,
    val priceChangePercentage1h: Double = 0.0,
    val priceChangePercentage24h: Double = 0.0,
    val priceChangePercentage7d: Double = 0.0,
    val priceChangePercentage30d: Double = 0.0,
    val priceChangePercentage1y: Double = 0.0,
)
