package app.coinfo.library.preferences

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

interface Preferences {

    /** Saves [Currency] to the global shared preferences. */
    fun saveCurrency(currency: Currency)

    /**
     * Loads globally saved [Currency] from shared preferences.
     * If no value previously saved returns [Currency.EUR]
     */
    fun loadCurrency(): Currency

    /** Saves [TimeInterval] to the global shared preferences. */
    fun saveTimeInterval(interval: TimeInterval)

    /**
     * Loads globally saved [TimeInterval] from shared preferences.
     * If no value previously saved returns [TimeInterval.DAY]
     */
    fun loadTimeInterval(): TimeInterval
}
