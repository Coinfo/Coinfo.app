package app.coinfo.feature.portfolios.mapper

import app.coinfo.feature.portfolios.model.UITransaction
import app.coinfo.feature.portfolios.model.UITransactionType
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

internal val TransactionType.asUITransactionType
    get() = when (this) {
        TransactionType.BUY -> UITransactionType.BUY
        TransactionType.SELL -> UITransactionType.SELL
        TransactionType.INTEREST_EARN -> UITransactionType.INTEREST_EARN
        TransactionType.STAKE_REWARD -> UITransactionType.STAKE_REWARD
        else -> throw IllegalStateException("Unsupported Transaction Type")
    }

internal val TransactionData.asUITransaction
    get() = UITransaction(
        id = transactionId,
        assetId = coinId,
        amount = amount,
        price = price,
        date = date,
        currency = currency,
        transactionType = type.asUITransactionType
    )
