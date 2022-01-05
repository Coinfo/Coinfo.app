package app.coinfo.portfolios.repo

import app.coinfo.library.database.Database
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType
import app.coinfo.portfolios.model.UIPortfolio
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.InputStreamReader

class PortfolioRepository(
    private val database: Database
) : Repository {

    override suspend fun readCryptoComAppCsv(filename: String?, stream: InputStream?) {

        val portfolioId = database.addPortfolio(
            PortfolioData(
                name = filename ?: "Crypto.com App (csv)",
                source = "crypto.com",
                data = System.currentTimeMillis()
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
        database.portfolios.map { portfolios -> portfolios.map { UIPortfolio(1, it.name) } }

    companion object {
        private const val INDEX_CURRENCY = 2
        private const val INDEX_AMOUNT = 3
        private const val INDEX_NATIVE_CURRENCY = 6
        private const val INDEX_NATIVE_AMOUNT = 7
        private const val INDEX_TRANSACTION_KIND = 9

        private const val TRANSACTION_TYPE_BUY = "crypto_purchase"
        private const val TRANSACTION_TYPE_SELL = "crypto_viban_exchange"
    }
}
