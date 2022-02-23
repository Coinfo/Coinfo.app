package app.coinfo.library.crypto.com.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CryptocomTransactionTypeTest {

    @Test
    fun `check fromString method returns correct values`() {
        assertThat(
            CryptocomTransactionType.fromString("crypto_purchase"),
            `is`(CryptocomTransactionType.CRYPTO_PURCHASE)
        )
        assertThat(
            CryptocomTransactionType.fromString("crypto_viban_exchange"),
            `is`(CryptocomTransactionType.CRYPTO_VIBAN_EXCHANGE)
        )
        assertThat(
            CryptocomTransactionType.fromString("viban_purchase"),
            `is`(CryptocomTransactionType.VIBAN_PURCHASE)
        )
        assertThat(
            CryptocomTransactionType.fromString("crypto_earn_program_created"),
            `is`(CryptocomTransactionType.CRYPTO_EARN_PROGRAM_CREATED)
        )
        assertThat(
            CryptocomTransactionType.fromString("crypto_earn_interest_paid"),
            `is`(CryptocomTransactionType.CRYPTO_EARN_INTEREST_PAID)
        )
        assertThat(
            CryptocomTransactionType.fromString("crypto_exchange"),
            `is`(CryptocomTransactionType.CRYPTO_EXCHANGE)
        )
        assertThat(
            CryptocomTransactionType.fromString("crypto_earn_program_withdrawn"),
            `is`(CryptocomTransactionType.CRYPTO_EARN_PROGRAM_WITHDRAWN)
        )
        assertThat(
            CryptocomTransactionType.fromString("lockup_lock"),
            `is`(CryptocomTransactionType.LOCKUP_LOCK)
        )
        assertThat(
            CryptocomTransactionType.fromString("mco_stake_reward"),
            `is`(CryptocomTransactionType.MCO_STAKE_REWARD)
        )
        assertThat(
            CryptocomTransactionType.fromString("xxx"),
            `is`(CryptocomTransactionType.UNKNOWN)
        )
    }
}
