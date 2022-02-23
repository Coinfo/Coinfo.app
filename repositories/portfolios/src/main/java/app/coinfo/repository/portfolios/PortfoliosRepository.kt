package app.coinfo.repository.portfolios

interface PortfoliosRepository {

    /**
     * Creates portfolio and saves it to the database.
     *
     * @param name The name of the portfolio
     **/
    suspend fun createPortfolio(name: String)
}
