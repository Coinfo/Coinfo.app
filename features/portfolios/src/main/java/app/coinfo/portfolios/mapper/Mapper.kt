package app.coinfo.portfolios.mapper

import app.coinfo.library.crypto.com.model.CryptocomTransactionData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType

internal fun CryptocomTransactionData.toDatabaseTransaction(
    transactionType: TransactionType,
    portfolioId: Long
) = TransactionData(
    transactionId = 0L,
    coinId = currency,
    portfolioId = portfolioId,
    amount = amount,
    price = nativeAmount,
    currency = nativeCurrency,
    date = timestamp,
    type = transactionType,
    note = description
)
