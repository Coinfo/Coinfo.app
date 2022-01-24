package app.coinfo.feature.portfolios.model

data class UITransaction(
    val id: Long,
    val assetId: String,
    val amount: Double,
    val price: Double,
    val currency: String,
    val date: Long,
    val transactionType: UITransactionType
)
