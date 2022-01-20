package app.coinfo.portfolios.repo.asset

import app.coinfo.portfolios.model.UITransactionData
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun getAssetInfo(assetId: String)

    suspend fun loadTransactions(portfolioId: Long, assetId: String): Flow<UITransactionData>
}
