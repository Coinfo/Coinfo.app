package app.coinfo.library.crypto.com.model

data class CryptocomTransactionData(
    /** Transaction timestamp UTC */
    val timestamp: Long,

    /** Transaction Description */
    val description: String,

    /** Currency */
    val currency: String,

    val amount: Double,

    val toCurrency: String,

    val toAmount: Double,

    val nativeCurrency: String,

    val nativeAmount: Double,

    val nativeAmountUsd: Double,

    val transactionType: CryptocomTransactionType
)
