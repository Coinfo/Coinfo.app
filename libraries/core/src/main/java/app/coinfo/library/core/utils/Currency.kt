package app.coinfo.library.core.utils

/** Enumeration of currency codes and symbols. */
enum class Currency(
    val code: String,
    val symbol: String,
    val number: Int
) {
    EUR("EUR", "€", 978),
    USD("USD", "$", 840),
    NA("N/A", "", 0);

    companion object {
        /** Returns symbol for the given currency code */
        fun toSymbol(code: String?) = values().firstOrNull { it.code.equals(code, true) }?.symbol ?: NA.symbol
        /** Returns code for the given currency symbol */
        fun toCode(symbol: String?) = values().firstOrNull { it.symbol == symbol }?.code ?: NA.code
    }
}
