package app.coinfo.library.database.mapper

import app.coinfo.library.database.entity.Portfolio
import app.coinfo.library.database.entity.Transaction
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType

internal val PortfolioData.asPortfolioEntity
    get() = Portfolio(displayName = name, source = source, date = date)

internal val Portfolio.asPortfolioData
    get() = PortfolioData(id = portfolioId, name = displayName, source = source, date = date)

internal val TransactionData.asTransactionEntity
    get() = Transaction(
        coinId = coinId,
        portfolioId = portfolioId,
        date = date,
        amount = amount,
        price = price,
        currency = currency,
        type = type.value,
        fee = fee,
        note = note
    )

internal val Transaction.asTransactionData
    get() = TransactionData(
        coinId = coinId,
        portfolioId = portfolioId,
        amount = amount,
        price = price,
        currency = currency,
        type = TransactionType.fromValue(type),
        date = date,
        fee = fee,
        note = note
    )
