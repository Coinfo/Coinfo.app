package app.coinfo.portfolios.repo

import app.coinfo.library.database.Database
import app.coinfo.library.database.model.TransactionData
import app.coinfo.library.database.model.TransactionType
import app.coinfo.library.database.model.TransactionType.BUY
import app.coinfo.library.database.model.TransactionType.DEPOSIT
import app.coinfo.library.database.model.TransactionType.INTEREST_EARN
import app.coinfo.library.database.model.TransactionType.SELL
import app.coinfo.library.logger.Logger
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
            createSOLEuroTransaction(0.01726027, 2.71, INTEREST_EARN, 1639614299000, "Crypto Earn"),
            createSOLEuroTransaction(0.01726027, 2.93, INTEREST_EARN, 1639009895000, "Crypto Earn"),
            createSOLEuroTransaction(0.01726027, 3.43, INTEREST_EARN, 1638406843000, "Crypto Earn"),
            createSOLEuroTransaction(0.01150685, 2.13, INTEREST_EARN, 1637802272000, "Crypto Earn"),
            createSOLEuroTransaction(0.01150685, 2.23, INTEREST_EARN, 1637196608000, "Crypto Earn"),
            createSOLEuroTransaction(0.01150685, 2.29, INTEREST_EARN, 1636591627000, "Crypto Earn"),
            createSOLEuroTransaction(0.01150685, 2.35, INTEREST_EARN, 1635986538000, "Crypto Earn"),
            createSOLEuroTransaction(-20.0, -3241.28, DEPOSIT, 1635399508000, "Crypto Earn Deposit"),
            createSOLEuroTransaction(-8.0, 233.28, SELL, 1625090104000, "SOL -> EUR"),
            createSOLEuroTransaction(-5.0, 144.36, SELL, 1624971383000, "SOL -> EUR"),
            createSOLEuroTransaction(-15.0, 405.05, SELL, 1624868379000, "SOL -> EUR"),
            createSOLEuroTransaction(48.0, 1988.88, BUY, 1621265796000, "EUR -> SOL"),
        )

        /** Crates SOL Transaction, for [PORTFOLIO_ID] and default currency EUR */
        private fun createSOLEuroTransaction(
            amount: Double,
            price: Double,
            type: TransactionType,
            date: Long,
            note: String
        ) = TransactionData("SOL", PORTFOLIO_ID, amount, price, "EUR", type, date, 0.0, note)
    }

    private val mockedDatabase: Database = mockk(relaxed = true)
    private val mockedLogger: Logger = mockk(relaxed = true)

    private val repository: Repository = PortfolioRepository(mockedDatabase, mockedLogger)

    @Test
    fun `check if SOL imported correct from csv to database`() = runBlocking {
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

    private fun getResourceAsStream(filename: String): InputStream? {
        return this.javaClass.classLoader?.getResourceAsStream(filename)
    }

    companion object {
        const val PORTFOLIO_ID = 1L
    }
}
