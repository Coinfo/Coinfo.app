package app.coinfo.repository.portfolios

import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.library.database.entity.PortfolioEntity
import app.coinfo.library.database.entity.TransactionEntity
import app.coinfo.library.database.enums.PortfolioType
import app.coinfo.repository.portfolios.model.Portfolio
import app.coinfo.repository.portfolios.model.Transaction

internal class PortfoliosRepositoryImpl(
    private val portfoliosDao: PortfoliosDao,
    private val transactionsDao: TransactionsDao,
) : PortfoliosRepository {

    override suspend fun createPortfolio(name: String) {
        portfoliosDao.insert(
            PortfolioEntity(
                name = name, date = System.currentTimeMillis(), type = PortfolioType.MANUAL
            )
        )
    }

    override suspend fun loadPortfolios(): List<Portfolio> =
        portfoliosDao.loadPortfolios().map {
            Portfolio(id = it.id, name = it.name)
        }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionsDao.insert(
            TransactionEntity(
                coinId = transaction.coinId,
                portfolioId = transaction.portfolioId,
                amount = transaction.amount,
                pricePerCoin = transaction.price,
                symbol = transaction.symbol,
                fee = transaction.fee,
                currency = transaction.currency
            )
        )
    }

    override suspend fun loadTransactions(portfolioId: Long): List<Transaction> {
        return transactionsDao.loadTransactions(portfolioId).map {
            Transaction(
                coinId = it.coinId,
                portfolioId = it.portfolioId,
                symbol = it.symbol,
                amount = it.amount,
                price = it.pricePerCoin,
                fee = it.fee,
                currency = it.currency
            )
        }
    }
}
