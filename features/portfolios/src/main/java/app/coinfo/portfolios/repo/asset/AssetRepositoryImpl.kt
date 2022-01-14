package app.coinfo.portfolios.repo.asset

import app.coinfo.library.cloud.Cloud

class AssetRepositoryImpl(
    private val cloud: Cloud,
) : AssetRepository {

    override suspend fun getAssetInfo(assetId: String) {
        cloud.getServerStatus()
    }
}
