package app.coinfo.feature.portfolios.repo.asset

import app.coinfo.feature.portfolios.mapper.asUITransaction
import app.coinfo.feature.portfolios.model.UITransaction
import app.coinfo.feature.portfolios.model.UITransactionData
import app.coinfo.feature.portfolios.model.UITransactionOverview
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.database.Database
import app.coinfo.library.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AssetRepositoryImpl(
    private val database: Database,
    private val cloud: Cloud,
    private val logger: Logger,
) : AssetRepository {

    override suspend fun getAssetInfo(assetId: String) {
        cloud.getServerStatus()
    }

    override suspend fun loadTransactions(
        portfolioId: Long,
        assetId: String
    ) = withContext(Dispatchers.Default) {
        logger.logDebug(TAG, "Load Transactions")
        val currentPrice = cloud.getCoinPrice(assetId)
        val transactions = mutableListOf<UITransaction>()
        val transactionOverview = UITransactionOverview(assetId)
        database.getTransactions(portfolioId, assetId).map {
            it.onEachIndexed { index, transaction ->
                if (index == 0) {
                    transactionOverview.currentPrice(currentPrice)
                }
                transactions.add(transaction.asUITransaction)
                transactionOverview.calculateOverview(
                    transaction.type,
                    transaction.currency,
                    transaction.amount,
                    transaction.price
                )
            }
            return@map UITransactionData(transactions, transactionOverview, currentPrice)
        }
    }

    companion object {
        private const val TAG = "PORT/AssetRepository"
    }
}