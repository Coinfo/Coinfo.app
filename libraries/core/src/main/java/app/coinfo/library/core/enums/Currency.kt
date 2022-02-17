package app.coinfo.library.core.enums

/** Enumeration of currency codes and symbols. */
enum class Currency(
    val uuid: String,
    val code: String,
    val symbol: String,
    val number: Int
) {
    EUR("466a214c-9022-11ec-b909-0242ac120002", "eur", "€", 978),
    USD("466a23fe-9022-11ec-b909-0242ac120002", "usd", "$", 840),
    NA("466a253e-9022-11ec-b909-0242ac120002", "N/A", "", 0);

    companion object {
        /** Returns symbol for the given currency code */
        fun toSymbol(code: String?) = values().firstOrNull { it.code.equals(code, true) }?.symbol ?: NA.symbol
        /** Returns code for the given currency symbol */
        fun toCode(symbol: String?) = values().firstOrNull { it.symbol == symbol }?.code ?: NA.code
    }
}
