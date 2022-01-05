package app.coinfo.library.database

import app.coinfo.library.database.database.CoinfoRoomDatabase
import app.coinfo.library.database.entity.Coin
import app.coinfo.library.database.entity.Transaction
import app.coinfo.library.database.mapper.asPortfolioEntity
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoinfoDatabase(
    private val database: CoinfoRoomDatabase,
    private val logger: Logger
) : Database {

    override suspend fun addPortfolio(data: PortfolioData): Long = withContext(Dispatchers.Default) {
        logger.logDebugEx(TAG, "Add Portfolio:")
        logger.logDebugEx(TAG, "   > Name   : ${data.name}")
        logger.logDebugEx(TAG, "   > Source : ${data.source}")
        logger.logDebugEx(TAG, "   > Date   : ${data.data}")

        return@withContext database.coinsDao().insertPortfolio(data.asPortfolioEntity).also {
            logger.logDebug(TAG, "    > ID     : $it")
        }
    }

    override suspend fun addTransaction(data: TransactionData) = withContext(Dispatchers.Default) {
        logger.logDebugEx(TAG, "Add Transaction:")
        logger.logDebugEx(TAG, "  > Coin ID      : ${data.coinId}")
        logger.logDebugEx(TAG, "  > Portfolio ID : ${data.portfolioId}")
        logger.logDebugEx(TAG, "  > Amount       : ${data.amount}")
        logger.logDebugEx(TAG, "  > Price        : ${data.price}")
        logger.logDebugEx(TAG, "  > Currency     : ${data.currency}")
        logger.logDebugEx(TAG, "  > Type         : ${data.type.value}")

        database.coinsDao().insertCoin(Coin(data.coinId))
        database.coinsDao().insertTransaction(
            Transaction(
                coinId = data.coinId,
                portfolioId = data.portfolioId,
                date = data.date,
                amount = data.amount,
                price = data.price,
                currency = data.currency,
                type = data.type.value
            )
        )
    }

    companion object {
        private const val TAG = "DB/Database"
    }
}
