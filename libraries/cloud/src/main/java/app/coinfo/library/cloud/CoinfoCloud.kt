package app.coinfo.library.cloud

import app.coinfo.library.cloud.model.ServerStatus
import app.coinfo.library.cloud.service.CoingeckoService
import app.coinfo.library.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class CoinfoCloud(
    private val service: CoingeckoService,
    private val logger: Logger,
) : Cloud {

    val coins = mutableMapOf<String, String>()

    override suspend fun getServerStatus() = withContext(Dispatchers.IO) {
        logger.logDebugEx(TAG, "Get Server Status:")
        return@withContext try {
            if (service.ping().status.equals("(V3) To the Moon!", true)) {
                logger.logDebugEx(TAG, "   > Success")
                ServerStatus.SUCCESS
            } else {
                logger.logDebugEx(TAG, "   > Error")
                ServerStatus.ERROR
            }
        } catch (e: IOException) {
            logger.logError(TAG, "Exception occurs while getting server status", e)
            ServerStatus.ERROR
        } catch (e: HttpException) {
            logger.logError(TAG, "Exception occurs while getting server status", e)
            ServerStatus.ERROR
        }
    }

    override suspend fun getCoinInfo(symbol: String) = withContext(Dispatchers.IO) {
        logger.logDebugEx(TAG, "Get Coin Info:")
        logger.logDebugEx(TAG, "   > Symbol: $symbol")
        initializeCoinsMapIfEmpty()
    }

    override suspend fun getCoinPrice(symbol: String) = withContext(Dispatchers.IO) {
        logger.logDebugEx(TAG, "Get Coin Price:")
        logger.logDebugEx(TAG, "   > Symbol     : $symbol")
        initializeCoinsMapIfEmpty()

        val id = coins[symbol.lowercase()]
        val prices = service.coinPrice(id, "eur")
        logger.logDebugEx(TAG, "   > Prices    : $prices")
        prices[id]?.get(CURRENCY) ?: throw IllegalStateException("Unable to fetch price of coin")
    }

    private suspend fun initializeCoinsMapIfEmpty() {
        if (coins.isEmpty()) {
            logger.logDebugEx(TAG, "Get List of Coins from the Server.")
            service.coinsList().onEach { coinListItem ->
//              logger.logDebugEx(TAG, "   > Coin List Item: $coinListItem")
                coins[coinListItem.symbol.lowercase()] = coinListItem.id
            }
        }
    }

    companion object {
        private const val TAG = "CLD/CoinfoCloud"
        private const val CURRENCY = "eur"
    }
}
