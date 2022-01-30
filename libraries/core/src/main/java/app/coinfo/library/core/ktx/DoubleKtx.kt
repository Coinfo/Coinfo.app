package app.coinfo.library.core.ktx

import java.util.Locale

/** Private */
private const val CENTI = 0.01
private const val HUNDRED = 100L
private const val THOUSAND = 100000L
private const val MILLION = 1000000L
private const val BILLION = 1000000000L
private const val TRILLION = 1000000000000L

/** Public */
const val DEFAULT_DIGITS_AFTER_COMMA = 2

/** Converts [Double] to [String] with given amount of [digits] after comma. */
fun Double.toString(digits: Int) = String.format(Locale.getDefault(), "%.${digits}f", this)

fun Double.toStringWithSuffix(digits: Int): String = when {
    this < THOUSAND -> this.toString(digits)
    this < MILLION -> "${(this.times(HUNDRED).div(THOUSAND).times(CENTI)).toString(digits)}K"
    this < BILLION -> "${(this.times(HUNDRED).div(MILLION).times(CENTI)).toString(digits)}M"
    this < TRILLION -> "${(this.times(HUNDRED).div(BILLION).times(CENTI)).toString(digits)}Bn"
    else -> "${(this.times(HUNDRED).div(TRILLION).times(CENTI)).toString(digits)}T"
}
