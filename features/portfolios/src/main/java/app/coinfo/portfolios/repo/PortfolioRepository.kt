package app.coinfo.portfolios.repo

import app.coinfo.library.crypto.com.CryptocomDataProviderImpl
import app.coinfo.library.crypto.com.model.CryptocomTransactionType
import app.coinfo.library.database.Database
import app.coinfo.library.database.model.PortfolioData
import app.coinfo.library.database.model.TransactionType
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.mapper.toDatabaseTransaction
import app.coinfo.portfolios.model.UIAsset
import app.coinfo.portfolios.model.UIPortfolio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream

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

        CryptocomDataProviderImpl().readData(stream).forEach { transaction ->
            when (transaction.transactionType) {
                CryptocomTransactionType.CRYPTO_PURCHASE ->
                    database.addTransaction(transaction.toDatabaseTransaction(TransactionType.BUY, portfolioId))
                CryptocomTransactionType.CRYPTO_VIBAN_EXCHANGE ->
                    database.addTransaction(transaction.toDatabaseTransaction(TransactionType.SELL, portfolioId))
                CryptocomTransactionType.CRYPTO_EARN_INTEREST_PAID ->
                    database.addTransaction(
                        transaction.toDatabaseTransaction(TransactionType.INTEREST_EARN, portfolioId)
                    )
                CryptocomTransactionType.MCO_STAKE_REWARD ->
                    database.addTransaction(
                        transaction.toDatabaseTransaction(TransactionType.INTEREST_EARN, portfolioId)
                    )
                CryptocomTransactionType.CRYPTO_EARN_PROGRAM_WITHDRAWN ->
                    database.addTransaction(transaction.toDatabaseTransaction(TransactionType.WITHDRAW, portfolioId))
                CryptocomTransactionType.CRYPTO_EARN_PROGRAM_CREATED ->
                    database.addTransaction(transaction.toDatabaseTransaction(TransactionType.DEPOSIT, portfolioId))
                CryptocomTransactionType.LOCKUP_LOCK -> { }
                CryptocomTransactionType.VIBAN_PURCHASE -> { }
                CryptocomTransactionType.CRYPTO_EXCHANGE -> { }
                CryptocomTransactionType.UNKNOWN -> { }
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
    }
}
