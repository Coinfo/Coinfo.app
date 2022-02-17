package app.coinfo.library.preferences

import app.coinfo.library.core.enums.TimeInterval

interface Preferences {

    /** Saves currency */
    fun saveCurrency(currency: String)

    /** Loads saved currency, if no value saved returns "EUR" */
    fun loadCurrency(): String

    /** Saves [TimeInterval] to the global shared preferences. */
    fun saveTimeInterval(interval: TimeInterval)

    /**
     * Loads globally saved [TimeInterval] from shared preferences.
     * If no value previously saved returns [TimeInterval.DAY]
     */
    fun loadTimeInterval(): TimeInterval
}
