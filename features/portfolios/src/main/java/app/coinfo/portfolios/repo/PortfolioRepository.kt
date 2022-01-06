package app.coinfo.portfolios.repo

import app.coinfo.library.database.Database
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.model.UIAsset
import app.coinfo.portfolios.model.UIPortfolio
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.InputStreamReader

class PortfolioRepository(
    private val database: Database,
    private val logger: Logger
) : Repository {

    override suspend fun readCryptoComAppCsv(filename: String?, stream: InputStream?) {

        val portfolioId = database.addPortfolio(
            PortfolioData(
                name = filename ?: "Crypto.com App (csv)",
                source = "crypto.com",
                date = System.currentTimeMillis()
            )
        )

        val csvReader = CSVReader(InputStreamReader(stream))
        var vv: Array<String>? = null

        csvReader.readNext()

        while (csvReader.readNext().also { vv = it } != null) {

            // Timestamp (UTC)

            // Transaction Description

            // Currency

            // Amount

            // To Currency

            // To Amount

            // Native Currency

            // Native Amount

            // Native Amount (in USD)

            // Transaction Kind

            vv?.let {
                val type = it[INDEX_TRANSACTION_KIND]
                if (type.equals(TRANSACTION_TYPE_BUY, true) ||
                    type.equals(TRANSACTION_TYPE_SELL, true)
                ) {

                    database.addTransaction(
                        TransactionData(
                            coinId = it[INDEX_CURRENCY],
                            portfolioId = portfolioId,
                            amount = it[INDEX_AMOUNT].toDouble(),
                            price = it[INDEX_NATIVE_AMOUNT].toDouble(),
                            currency = it[INDEX_NATIVE_CURRENCY],
                            type = if (type.equals(TRANSACTION_TYPE_BUY, true)) {
                                TransactionType.BUY
                            } else TransactionType.SELL,
                        )
                    )
                }
            }
        }
    }

    override fun loadPortfolios(): Flow<List<UIPortfolio>> =
        database.portfolios.map { portfolios -> portfolios.map { UIPortfolio(it.id, it.name) } }

    override fun loadAssets(portfolioId: Long): Flow<List<UIAsset>> =
        database.getTransactions(portfolioId).map { transactions ->
            val assets = hashMapOf<String, UIAsset>()
            logger.logDebugEx(TAG, "Loading Assets:")
            logger.logDebugEx(TAG, "   > Total Assets Loaded: ${transactions.size}")
            transactions.onEach { transaction ->
                val asset = assets[transaction.coinId]
                if (asset == null) {
                    assets[transaction.coinId] = UIAsset(
                        id = transaction.coinId,
                        name = transaction.coinId,
                        price = 0.0,
                        totalPrice = 0.0,
                        totalHolding = transaction.amount,
                        percentage = 0.0f
                    )
                } else {
                    assets[transaction.coinId] = asset.copy(totalHolding = asset.totalHolding + transaction.amount)
                }
            }

            return@map assets.values.toList()
        }

    companion object {
        private const val TAG = "PORT/PortfolioRepository"

        private const val INDEX_CURRENCY = 2
        private const val INDEX_AMOUNT = 3
        private const val INDEX_NATIVE_CURRENCY = 6
        private const val INDEX_NATIVE_AMOUNT = 7
        private const val INDEX_TRANSACTION_KIND = 9

        private const val TRANSACTION_TYPE_BUY = "crypto_purchase"
        private const val TRANSACTION_TYPE_SELL = "crypto_viban_exchange"
    }
}
