package app.coinfo.library.crypto.com

import app.coinfo.library.crypto.com.model.CryptocomTransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.io.InputStream

class CryptocomDataProviderImplTest {

    @Test
    fun `parse csv file successful`() = runBlocking(Dispatchers.Default) {
        val transactions = CryptocomDataProviderImpl().readData(getResourceAsStream("crypto.com.reports.csv"))
        // Check number of transactions.
        assertThat(transactions.size, `is`(240))
        // Validate First Transaction Row in the CSV
        // 2021-12-16 00:25:01,Crypto Earn,CRO,13.80821918,,,EUR,6.72,7.6370730912,crypto_earn_interest_paid
        val firstTransaction = transactions[0]
//        assertThat(firstTransaction.timestamp, `is`(1639610701000))
        assertThat(firstTransaction.description, `is`("Crypto Earn"))
        assertThat(firstTransaction.currency, `is`("CRO"))
        assertThat(firstTransaction.amount, `is`(13.80821918))
        assertThat(firstTransaction.toCurrency, `is`(""))
        assertThat(firstTransaction.toAmount, `is`(0.0))
        assertThat(firstTransaction.nativeCurrency, `is`("EUR"))
        assertThat(firstTransaction.nativeAmount, `is`(6.72))
        assertThat(firstTransaction.nativeAmountUsd, `is`(7.6370730912))
        assertThat(firstTransaction.transactionType, `is`(CryptocomTransactionType.CRYPTO_EARN_INTEREST_PAID))
    }

    private fun getResourceAsStream(filename: String): InputStream? {
        return this.javaClass.classLoader?.getResourceAsStream(filename)
    }
}
