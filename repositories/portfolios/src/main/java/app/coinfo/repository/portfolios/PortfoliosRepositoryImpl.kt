package app.coinfo.repository.portfolios

import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.entity.PortfolioEntity
import app.coinfo.library.database.enums.PortfolioType
import app.coinfo.repository.portfolios.model.Portfolio

internal class PortfoliosRepositoryImpl(
    private val portfoliosDao: PortfoliosDao
) : PortfoliosRepository {

    override suspend fun createPortfolio(name: String) {
        portfoliosDao.insert(
            PortfolioEntity(
                name = name, date = System.currentTimeMillis(), type = PortfolioType.MANUAL
            )
        )
    }

    override suspend fun loadPortfolios(): List<Portfolio> =
        portfoliosDao.loadPortfolios().map {
            Portfolio(id = it.id, name = it.name)
        }
}
