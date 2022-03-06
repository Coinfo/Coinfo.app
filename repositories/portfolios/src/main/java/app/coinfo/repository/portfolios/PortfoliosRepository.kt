package app.coinfo.repository.portfolios

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

    suspend fun loadTransactions(portfolioId: Long): List<Transaction>
}
