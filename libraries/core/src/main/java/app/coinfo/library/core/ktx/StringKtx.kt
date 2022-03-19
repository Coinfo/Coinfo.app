package app.coinfo.library.core.ktx

import java.lang.NumberFormatException

fun String.toDoubleOrZero(): Double = try {
    this.toDouble()
} catch (e: NumberFormatException) {
    0.000000000000000000000
}
