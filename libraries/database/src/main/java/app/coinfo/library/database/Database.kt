package app.coinfo.library.database

import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface Database {

    val portfolios: Flow<List<PortfolioData>>

    fun getTransactions(portfolioId: Long): Flow<List<TransactionData>>

    suspend fun addPortfolio(data: PortfolioData): Long

    suspend fun addTransaction(data: TransactionData)
}
