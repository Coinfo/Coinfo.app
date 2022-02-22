package app.coinfo.library.cloud

import app.coinfo.library.cloud.model.Coin
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.HistoricalMarketData
import app.coinfo.library.cloud.model.SearchResults
import app.coinfo.library.cloud.model.ServerStatus
import app.coinfo.library.cloud.model.TrendingResults
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

interface Cloud {

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
