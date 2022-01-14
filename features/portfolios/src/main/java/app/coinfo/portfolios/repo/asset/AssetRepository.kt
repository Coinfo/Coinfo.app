package app.coinfo.portfolios.repo.asset

interface AssetRepository {

    suspend fun getAssetInfo(assetId: String)
}
