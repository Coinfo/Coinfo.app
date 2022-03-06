package app.coinfo.repository.portfolios.model

data class Transaction(
    val coinId: String,
    val portfolioId: Long,
    val symbol: String,
    val amount: Double,
    val pricePerCoin: Double,
)
