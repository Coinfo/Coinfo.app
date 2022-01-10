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
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PortfolioRepositoryTest {

    private val mockedDb: Database = mockk(relaxed = true)
    private val mockedLogger: Logger = mockk(relaxed = true)

    private val repository: Repository = PortfolioRepository(mockedDb, mockedLogger)

//    @Test
//    fun `check if SOL imported correct from csv to database`() = runBlocking {
//        coEvery { mockedDb.addPortfolio(any()) } returns PORTFOLIO_ID
//
//        repository.readCryptoComAppCsv("crypto.com.sol.csv", getResourceAsStream("crypto.com.sol.csv"))
//
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 2.71, INTEREST_EARN, 1639610699000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 2.93, INTEREST_EARN, 1639006295000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01726027, 3.43, INTEREST_EARN, 1638403243000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.13, INTEREST_EARN, 1637798672000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.23, INTEREST_EARN, 1637193008000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.29, INTEREST_EARN, 1636588027000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(0.01150685, 2.35, INTEREST_EARN, 1635982938000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-20.0, -3241.28, DEPOSIT, 1635392308000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-8.0, 233.28, SELL, 1625082904000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-5.0, 144.36, SELL, 1624964183000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(-15.0, 405.05, SELL, 1624861179000)) }
//        coVerify { mockedDb.addTransaction(createSOLEuroTransaction(48.0, 1988.88, BUY, 1621258596000)) }
//    }

    @Test
    fun aaa() {
        System.out.println("-------------------------------------")
        System.out.println("aaa")
        val dateFormatLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val utcDate = dateFormatLocal.parse("2021-12-16 00:24:59")
        assertThat(utcDate.time, `is`(1639610699000))
    }

    @Test
    fun bbb() {
        System.out.println("-------------------------------------")
        System.out.println("bbb")
        val dateFormatLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val utcDate = dateFormatLocal.parse("2021-12-16 00:24:59")
        assertThat(utcDate.time, `is`(1639610699000))
    }

    @Test
    fun ddd() {
        System.out.println("-------------------------------------")
        System.out.println("ddd")
        val dateFormatLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val utcDate = dateFormatLocal.parse("2021-12-16 00:24:59")

        assertThat(utcDate.time, `is`(1639610699000))
    }

    @Test
    fun ccc() {
        System.out.println("-------------------------------------")
        System.out.println("ccc")
        val dateFormatLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT)
        val utcDate = dateFormatLocal.parse("2021-12-16 00:24:59")
        assertThat(utcDate.time, `is`(1639610699000))
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
