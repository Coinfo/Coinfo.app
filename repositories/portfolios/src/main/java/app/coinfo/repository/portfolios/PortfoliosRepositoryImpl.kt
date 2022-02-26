package app.coinfo.repository.portfolios

import app.coinfo.library.database.Database

internal class PortfoliosRepositoryImpl(
    private val database: Database
) : PortfoliosRepository {

    override suspend fun createPortfolio(name: String) {
        database.savePortfolio(name)
    }
}
