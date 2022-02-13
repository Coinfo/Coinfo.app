package app.coinfo.library.cloud

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.model.Coin
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.HistoricalMarketData
import app.coinfo.library.cloud.model.ServerStatus

interface Cloud {

    /** Returns true if server is up and running; otherwise false. */
    suspend fun getServerStatus(): ServerStatus

    /** Returns list of coins */
    suspend fun getCoinInfo(symbol: String)

    /** Returns Coin Price in Euro */
    suspend fun getCoinPrice(symbol: String): Double

    suspend fun loadCoins(currency: String): List<Coin>

    /** Returns [CoinData] for the given coin [id]. */
    suspend fun getCoinData(id: String): CoinData

    suspend fun getCoinHistoricalMarketData(
        id: String,
        currency: Currency,
        timeInterval: TimeInterval,
    ): HistoricalMarketData
}
