package app.coinfo.library.database.model

data class TransactionData(
    val transactionId: Long = 0L,
    val coinId: String,
    val portfolioId: Long,
    val amount: Double,
    val price: Double,
    val currency: String,
    val type: TransactionType,
    val date: Long = 0L,
    val fee: Double = 0.0,
    val note: String = ""
)
