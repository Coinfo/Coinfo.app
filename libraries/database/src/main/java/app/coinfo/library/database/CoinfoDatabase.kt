package app.coinfo.library.database

import app.coinfo.library.database.database.CoinfoRoomDatabase
import app.coinfo.library.database.mapper.asPortfolioData
import app.coinfo.library.database.mapper.asPortfolioEntity
import app.coinfo.library.database.mapper.asTransactionData
import app.coinfo.library.database.mapper.asTransactionEntity
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class CoinfoDatabase(
    private val database: CoinfoRoomDatabase,
    private val logger: Logger
) : Database {

    override val portfolios: Flow<List<PortfolioData>>
        get() = database.portfoliosDao().getPortfolios().map { portfolios ->
            logger.logDebugEx(TAG, "Get Portfolios:")
            logger.logDebugEx(TAG, "   > Portfolios Count : ${portfolios.size}")
            portfolios.map { portfolio -> portfolio.asPortfolioData }
        }.flowOn(Dispatchers.Default)

    override fun getTransactions(portfolioId: Long): Flow<List<TransactionData>> {
        return database.transactionsDao().getTransactions(portfolioId).map { transactions ->
            logger.logDebugEx(TAG, "Get Transactions:")
            logger.logDebugEx(TAG, "   > Portfolio ID       : $portfolioId")
            logger.logDebugEx(TAG, "   > Transactions Count : ${transactions.size}")
            transactions.map { transaction -> transaction.asTransactionData }
        }.flowOn(Dispatchers.Default)
    }

    override fun getTransactions(portfolioId: Long, assetId: String): Flow<List<TransactionData>> {
        return database.transactionsDao().getTransactions(portfolioId, assetId).map { transactions ->
            logger.logDebugEx(TAG, "Get Transactions:")
            logger.logDebugEx(TAG, "   > Portfolio ID       : $portfolioId")
            logger.logDebugEx(TAG, "   > Asset ID           : $assetId")
            logger.logDebugEx(TAG, "   > Transactions Count : ${transactions.size}")
            transactions.map { transaction -> transaction.asTransactionData }
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun addPortfolio(data: PortfolioData): Long = withContext(Dispatchers.Default) {
        logger.logDebugEx(TAG, "Add Portfolio:")
        logger.logDebugEx(TAG, "   > Name   : ${data.name}")
        logger.logDebugEx(TAG, "   > Source : ${data.source}")
        logger.logDebugEx(TAG, "   > Date   : ${data.date}")

        return@withContext database.portfoliosDao().insertPortfolio(data.asPortfolioEntity).also {
            logger.logDebug(TAG, "   > ID     : $it")
        }
    }

    override suspend fun addTransaction(data: TransactionData) = withContext(Dispatchers.Default) {
        logger.logDebugEx(TAG, "Add Transaction:")
        logger.logDebugEx(TAG, "   > Coin ID      : ${data.coinId}")
        logger.logDebugEx(TAG, "   > Portfolio ID : ${data.portfolioId}")
        logger.logDebugEx(TAG, "   > Amount       : ${data.amount}")
        logger.logDebugEx(TAG, "   > Price        : ${data.price}")
        logger.logDebugEx(TAG, "   > Currency     : ${data.currency}")
        logger.logDebugEx(TAG, "   > Type         : ${data.type.value}")

        database.transactionsDao().insertTransaction(data.asTransactionEntity)
    }

    companion object {
        private const val TAG = "DB/Database"
    }
}
