package app.coinfo.library.crypto.com.model

enum class CryptocomTransactionType(
    val value: String
) {
    CRYPTO_PURCHASE("crypto_purchase"),
    CRYPTO_VIBAN_EXCHANGE("crypto_viban_exchange"),
    VIBAN_PURCHASE("viban_purchase"),
    CRYPTO_EARN_PROGRAM_CREATED("crypto_earn_program_created"),
    CRYPTO_EARN_INTEREST_PAID("crypto_earn_interest_paid"),
    CRYPTO_EXCHANGE("crypto_exchange"),
    CRYPTO_EARN_PROGRAM_WITHDRAWN("crypto_earn_program_withdrawn"),
    LOCKUP_LOCK("lockup_lock"),
    MCO_STAKE_REWARD("mco_stake_reward"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): CryptocomTransactionType =
            values().firstOrNull { it.value == value } ?: UNKNOWN
    }
}
