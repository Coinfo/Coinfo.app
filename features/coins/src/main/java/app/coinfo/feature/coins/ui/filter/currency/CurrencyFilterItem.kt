package app.coinfo.feature.coins.ui.filter.currency

import androidx.annotation.StringRes
import app.coinfo.feature.coins.R

enum class CurrencyFilterItem(val value: String, val symbol: String, @StringRes val resId: Int) {
    EUR("eur", "€", R.string.text_filter_currency_eur),
    USD("usd", "$", R.string.text_filter_currency_usd);

    companion object {
        /**
         * Converts [String] [value] to [CurrencyFilterItem] if
         * no value matching returns default value [EUR]
         **/
        fun fromValue(value: String?) = values().firstOrNull { it.value == value } ?: EUR
    }
}
