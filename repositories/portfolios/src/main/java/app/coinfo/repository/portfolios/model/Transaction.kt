package app.coinfo.repository.portfolios.model

import app.coinfo.library.core.enums.Currency

data class Transaction(
    val coinId: String,
    val portfolioId: Long,
    val symbol: String,
    val amount: Double,
    val price: Double,
    val fee: Double,
    val currency: Currency
)
