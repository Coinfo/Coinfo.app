package app.coinfo.library.database

interface Database {

    /**
     *  Saves portfolio with given name to the database
     *
     *  @param name The name ot the portfolio
     */
    suspend fun savePortfolio(name: String)
}
