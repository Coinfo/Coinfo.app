package app.coinfo.feature.portfolios.repo.asset

import app.coinfo.feature.portfolios.model.UITransactionData
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun getAssetInfo(assetId: String)

    suspend fun loadTransactions(portfolioId: Long, assetId: String): Flow<UITransactionData>
}
