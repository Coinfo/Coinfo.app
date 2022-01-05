package app.coinfo.library.database

import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData

interface Database {

    suspend fun addPortfolio(data: PortfolioData): Long

    suspend fun addTransaction(data: TransactionData)
}
