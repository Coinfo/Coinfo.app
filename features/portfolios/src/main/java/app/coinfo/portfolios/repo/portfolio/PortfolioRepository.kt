package app.coinfo.portfolios.repo.portfolio

import app.coinfo.portfolios.model.UIAsset
import app.coinfo.portfolios.model.UIPortfolio
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface PortfolioRepository {

    suspend fun readCryptoComAppCsv(filename: String?, stream: InputStream?)

    fun loadPortfolios(): Flow<List<UIPortfolio>>

    fun loadAssets(portfolioId: Long): Flow<List<UIAsset>>
}
