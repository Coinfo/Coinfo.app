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
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.InputStream

class PortfolioRepositoryTest {

    private val mockedDb: Database = mockk(relaxed = true)
    private val mockedLogger: Logger = mockk(relaxed = true)

    private val repository: Repository = PortfolioRepository(mockedDb, mockedLogger)

    @Test
    fun `check if SOL imported correct from csv to database`() = runBlocking {
        coEvery { mockedDb.addPortfolio(any()) } returns PORTFOLIO_ID

        repository.readCryptoComAppCsv("crypto.com.sol.csv", getResourceAsStream("crypto.com.sol.csv"))

        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 2.71, INTEREST_EARN, 1639614299000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 2.93, INTEREST_EARN, 1639009895000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 3.43, INTEREST_EARN, 1638406843000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.13, INTEREST_EARN, 1637802272000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.23, INTEREST_EARN, 1637196608000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.29, INTEREST_EARN, 1636591627000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.35, INTEREST_EARN, 1635986538000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-20.0, -3241.28, DEPOSIT, 1635399508000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-8.0, 233.28, SELL, 1625090104000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-5.0, 144.36, SELL, 1624971383000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-15.0, 405.05, SELL, 1624868379000)) }
        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(48.0, 1988.88, BUY, 1621265796000)) }
    }

    private fun getResourceAsStream(filename: String): InputStream? {
        return this.javaClass.classLoader?.getResourceAsStream(filename)
    }

    private fun createSOLEuroTransaction(amount: Double, price: Double, type: TransactionType, date: Long) =
        TransactionData("SOL", PORTFOLIO_ID, amount, price, "EUR", type, date, 0.0)

    companion object {
        const val PORTFOLIO_ID = 1L
    }
}
