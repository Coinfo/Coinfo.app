package app.coinfo.portfolios.repo.asset

import app.coinfo.library.cloud.Cloud
import app.coinfo.library.database.Database
import app.coinfo.portfolios.model.UITransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AssetRepositoryImpl(
    private val database: Database,
    private val cloud: Cloud,
) : AssetRepository {

    override suspend fun getAssetInfo(assetId: String) {
        cloud.getServerStatus()
    }

    override suspend fun loadTransactions(
        portfolioId: Long,
        assetId: String
    ) = withContext(Dispatchers.Default) {
        database.getTransactions(portfolioId, assetId).map { transactions ->
            transactions.map { transaction ->
                UITransaction(
                    id = transaction.transactionId,
                    assetId = transaction.coinId,
                    amount = transaction.amount,
                    price = transaction.price,
                    date = transaction.date,
                    currency = transaction.currency
                )
            }
        }
    }
}
