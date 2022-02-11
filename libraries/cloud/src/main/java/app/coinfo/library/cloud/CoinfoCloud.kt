package app.coinfo.library.cloud

import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.mapper.asCoin
import app.coinfo.library.cloud.mapper.asHistoricalMarketData
import app.coinfo.library.cloud.model.Coin
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

    override suspend fun loadCoins(currency: String): List<Coin> {
        return service.coinMarkets(
            currency,
            "market_cap_desc",
            LOAD_COINS_PER_PAGE, LOAD_PAGE,
            "1h,24h,7d,14d,30d,200d,1y"
        ).map {
            Coin(
                id = it.id,
                name = it.name,
                symbol = it.symbol,
                image = it.image,
                currentPrice = it.currentPrice,
                marketCap = it.marketCap,
                marketCapRank = it.marketCapRank,
                priceChangePercentage1h = it.priceChangePercentage1h ?: 0.0,
                priceChangePercentage24h = it.priceChangePercentage24h ?: 0.0,
                priceChangePercentage7d = it.priceChangePercentage7d ?: 0.0,
                priceChangePercentage14d = it.priceChangePercentage14d ?: 0.0,
                priceChangePercentage30d = it.priceChangePercentage30d ?: 0.0,
                priceChangePercentage200d = it.priceChangePercentage200d ?: 0.0,
                priceChangePercentage1y = it.priceChangePercentage200d ?: 0.0
            )
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun getCoinData(id: String) = withContext(Dispatchers.IO) {
        return@withContext service.coinCurrentData(
            id = id,
            marketData = true,
        ).asCoin
    }

    override suspend fun getCoinHistoricalMarketData(
        id: String,
        currency: Currency,
        timeInterval: TimeInterval,
    ) = withContext(Dispatchers.IO) {
        return@withContext service.historicalMarketData(
            id = id,
            vsCurrency = currency.value,
            days = when (timeInterval) {
                TimeInterval.DAY, TimeInterval.HOUR -> "1"
                TimeInterval.WEEK -> "7"
                TimeInterval.MONTH -> "30"
                TimeInterval.TWO_MONTHS -> "60"
                TimeInterval.YEAR -> "360"
            },
        ).asHistoricalMarketData
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        private const val LOAD_COINS_PER_PAGE = 100
        private const val LOAD_PAGE = 1
        private const val CURRENCY = "eur"
    }
}
