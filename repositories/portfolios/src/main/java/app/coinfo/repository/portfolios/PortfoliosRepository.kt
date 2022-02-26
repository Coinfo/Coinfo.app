package app.coinfo.repository.portfolios

import app.coinfo.repository.portfolios.model.Portfolio

interface PortfoliosRepository {

    /**
     * Creates portfolio and saves it to the database.
     *
     * @param name The name of the portfolio
     **/
    suspend fun createPortfolio(name: String)

    suspend fun loadPortfolios(): List<Portfolio>
}
