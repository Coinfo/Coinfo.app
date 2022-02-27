package app.coinfo.repository.coins

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.repository.coins.model.Coin
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.coins.model.HistoricalMarketData
import app.coinfo.repository.coins.model.SearchResults
import app.coinfo.repository.coins.model.ServerStatus
import app.coinfo.repository.coins.model.TrendingResults

interface CoinsRepository {

    /** Returns true if server is up and running; otherwise false. */
    suspend fun getServerStatus(): ServerStatus

    /** Returns list of coins */
    suspend fun getCoinInfo(symbol: String)

    /** Returns Coin Price in Euro */
    suspend fun getCoinPrice(symbol: String): Double

    suspend fun loadCoins(ids: List<String>, currency: Currency): List<Coin>

    /** Returns [CoinData] for the given coin [id]. */
    suspend fun getCoinData(id: String): CoinData

    suspend fun getCoinHistoricalMarketData(
        id: String,
        currency: Currency,
        timeInterval: TimeInterval,
    ): HistoricalMarketData

    suspend fun search(query: String): SearchResults

    suspend fun getTrending(): TrendingResults
}
