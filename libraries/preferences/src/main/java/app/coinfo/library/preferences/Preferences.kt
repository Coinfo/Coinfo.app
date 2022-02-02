package app.coinfo.library.preferences

interface Preferences {

    /** Saves currency */
    fun saveCurrency(currency: String)

    /** Loads saved currency, if no value saved returns "EUR" */
    fun loadCurrency(): String

    fun saveChangeTimeline(value: String)

    fun loadChangeTimeline(): String?
}
