package app.coinfo.repository.portfolios.model

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType

data class Transaction(
    val coinId: String,
    val portfolioId: Long,
    val symbol: String,
    val amount: Double,
    val price: Double,
    val fee: Double,
    val currency: Currency,
    val type: TransactionType,
)
