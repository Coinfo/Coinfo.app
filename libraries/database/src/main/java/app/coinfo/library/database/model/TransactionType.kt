package app.coinfo.library.database.model

enum class TransactionType(val value: Int) {
    BUY(0),
    SELL(1),
    DEPOSIT(2),
    INTEREST_EARN(3),
    INTEREST_PAID(4),
    WITHDRAW(5),
    UNKNOWN(6);

    companion object {
        fun fromValue(value: Int) = values().firstOrNull { it.value == value } ?: UNKNOWN
    }
}
