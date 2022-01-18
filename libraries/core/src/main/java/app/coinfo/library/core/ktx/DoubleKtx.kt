package app.coinfo.library.core.ktx

import java.util.Locale

const val DEFAULT_DIGITS_AFTER_COMMA = 2

/** Converts [Double] to [String] with given amount of [digits] after comma. */
fun Double.toString(digits: Int) = String.format(Locale.getDefault(), "%.${digits}f", this)
