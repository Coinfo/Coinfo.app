package app.coinfo.portfolios.repo

import app.coinfo.library.database.Database
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType
import app.coinfo.library.database.model.TransactionType.BUY
import app.coinfo.library.database.model.TransactionType.DEPOSIT
import app.coinfo.library.database.model.TransactionType.INTEREST_EARN
import app.coinfo.library.database.model.TransactionType.SELL
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.repo.portfolio.PortfolioRepository
import app.coinfo.portfolios.repo.portfolio.PortfolioRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.io.InputStream

class PortfolioRepositoryTest {

    private object TRANSACTIONS {
        /** List of SOL Transactions. */
        val SOL = listOf(
            createTransaction("SOL", 0.01726027, 2.71, INTEREST_EARN, 1639614299000, "Crypto Earn"),
            createTransaction("SOL", 0.01726027, 2.93, INTEREST_EARN, 1639009895000, "Crypto Earn"),
            createTransaction("SOL", 0.01726027, 3.43, INTEREST_EARN, 1638406843000, "Crypto Earn"),
            createTransaction("SOL", 0.01150685, 2.13, INTEREST_EARN, 1637802272000, "Crypto Earn"),
            createTransaction("SOL", 0.01150685, 2.23, INTEREST_EARN, 1637196608000, "Crypto Earn"),
            createTransaction("SOL", 0.01150685, 2.29, INTEREST_EARN, 1636591627000, "Crypto Earn"),
            createTransaction("SOL", 0.01150685, 2.35, INTEREST_EARN, 1635986538000, "Crypto Earn"),
            createTransaction("SOL", -20.0, -3241.28, DEPOSIT, 1635399508000, "Crypto Earn Deposit"),
            createTransaction("SOL", -8.0, 233.28, SELL, 1625090104000, "SOL -> EUR"),
            createTransaction("SOL", -5.0, 144.36, SELL, 1624971383000, "SOL -> EUR"),
            createTransaction("SOL", -15.0, 405.05, SELL, 1624868379000, "SOL -> EUR"),
            createTransaction("SOL", 48.0, 1988.88, BUY, 1621265796000, "EUR -> SOL"),
        )

        /** List of SHIB Transactions */
        val SHIB = listOf(
            createTransaction("SHIB", -20098630.0, 469.88, SELL, 1634045042000, "SHIB -> EUR"),
            createTransaction("SHIB", 6575.34246575, 0.16, INTEREST_EARN, 1633998204000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.11, INTEREST_EARN, 1633480201000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.05, INTEREST_EARN, 1632876012000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1632269740000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1631667131000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1631063067000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1630458043000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.05, INTEREST_EARN, 1629852395000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.05, INTEREST_EARN, 1629245153000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.05, INTEREST_EARN, 1628643411000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1628036112000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1627434408000, "Crypto Earn"),
            createTransaction("SHIB", 7671.23287671, 0.04, INTEREST_EARN, 1626827697000, "Crypto Earn"),
            createTransaction("SHIB", 20000000.0, 127.65, BUY, 1626197194000, "EUR -> SHIB"),
            createTransaction("SHIB", -15697987.7551, 298.78, SELL, 1620665864000, "SHIB -> CRO"),
            createTransaction("SHIB", 15697987.7551, 200.0, BUY, 1620599658000, "EUR -> SHIB"),
        )

        /** Crates SOL Transaction, for [PORTFOLIO_ID] and default currency EUR */
        private fun createTransaction(
            coinID: String,
            amount: Double,
            price: Double,
            type: TransactionType,
            date: Long,
            note: String
        ) = TransactionData(0L, coinID, PORTFOLIO_ID, amount, price, "EUR", type, date, 0.0, note)
    }

    private val mockedDatabase: Database = mockk(relaxed = true)
    private val mockedLogger: Logger = mockk(relaxed = true)

    private val repository: PortfolioRepository = PortfolioRepositoryImpl(mockedDatabase, mockedLogger)

    @Test
    fun `check if SOL imported correct from csv to database`() = runBlocking {
        coEvery { mockedDatabase.addPortfolio(any()) } returns PORTFOLIO_ID

        repository.readCryptoComAppCsv("crypto.com.shib.csv", getResourceAsStream("crypto.com.shib.csv"))

        coVerify { TRANSACTIONS.SHIB.forEach { mockedDatabase.addTransaction(it) } }
    }

    @Test
    fun `check if SHIB imported correct from csv database`() = runBlocking {
        coEvery { mockedDatabase.addPortfolio(any()) } returns PORTFOLIO_ID

        repository.readCryptoComAppCsv("crypto.com.sol.csv", getResourceAsStream("crypto.com.sol.csv"))

        coVerify { TRANSACTIONS.SOL.forEach { mockedDatabase.addTransaction(it) } }
    }

    @Test
    fun `validate that total holding of SOL and other data is correct`() = runBlocking {
        every { mockedDatabase.getTransactions(PORTFOLIO_ID) } returns flow { emit(TRANSACTIONS.SOL) }
        repository.loadAssets(PORTFOLIO_ID).collect { assets ->
            // Check that list of assets contains exact 1 asset (which is SOL)
            assertThat(assets.size, `is`(1))
            // Get SOL asset
            val asset = assets[0]
            assertThat(asset.id, `is`("SOL"))
            assertThat(asset.price, `is`(0.0))
            assertThat(asset.totalPrice, `is`(0.0))
            assertThat(asset.percentage, `is`(0.0f))
            assertThat(asset.totalHolding, `is`(20.09780821))
        }
    }

    @Test
    fun `verify SHIB data`() = runBlocking {
        every { mockedDatabase.getTransactions(PORTFOLIO_ID) } returns flow { emit(TRANSACTIONS.SHIB) }
        repository.loadAssets(PORTFOLIO_ID).collect { assets ->
            // Check that list of assets contains exact 1 asset (which is SOL)
            assertThat(assets.size, `is`(1))
            // Get SOL asset
            val asset = assets[0]
            assertThat(asset.id, `is`("SHIB"))
            assertThat(asset.price, `is`(0.0))
            assertThat(asset.totalPrice, `is`(0.0))
            assertThat(asset.percentage, `is`(0.0f))
            assertThat(asset.totalHolding, `is`(0.13698627799749374))
        }
    }

    private fun getResourceAsStream(filename: String): InputStream? {
        return this.javaClass.classLoader?.getResourceAsStream(filename)
    }

    companion object {
        const val PORTFOLIO_ID = 1L
    }
}
