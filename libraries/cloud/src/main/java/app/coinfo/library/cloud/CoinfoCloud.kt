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

    companion object {
        private const val TAG = "CLD/CoinfoCloud"
    }
}
