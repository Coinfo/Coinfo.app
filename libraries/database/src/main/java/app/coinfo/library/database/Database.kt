package app.coinfo.library.database

import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface Database {

    val portfolios: Flow<List<PortfolioData>>

    suspend fun addPortfolio(data: PortfolioData): Long

    suspend fun addTransaction(data: TransactionData)
}
