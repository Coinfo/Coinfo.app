package app.coinfo.repository.portfolios

import app.coinfo.repository.portfolios.model.Assets
import app.coinfo.repository.portfolios.model.Portfolio
import app.coinfo.repository.portfolios.model.Transaction

interface PortfoliosRepository {

    /**
     * Creates portfolio and saves it to the database.
     *
     * @param name The name of the portfolio
     **/
    suspend fun createPortfolio(name: String)

    suspend fun loadPortfolios(): List<Portfolio>

    /**
     * Adds transaction
     *
     * @param transaction The transaction to be added.
     */
    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun loadTransactions(portfolioId: Long, coinId: String): List<Transaction>

    suspend fun loadTransaction(id: Long): Transaction

    suspend fun deleteTransaction(id: Long)

    suspend fun loadAssets(portfolioId: Long): Assets
}
