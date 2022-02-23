package app.coinfo.library.database.model

enum class TransactionType(val value: Int) {
    BUY(0),
    SELL(1),
    DEPOSIT(2),
    INTEREST_EARN(3),
    STAKE_REWARD(4),
    INTEREST_PAID(5),
    WITHDRAW(6),
    UNKNOWN(7);

    companion object {
        fun fromValue(value: Int) = values().firstOrNull { it.value == value } ?: UNKNOWN
    }
}
