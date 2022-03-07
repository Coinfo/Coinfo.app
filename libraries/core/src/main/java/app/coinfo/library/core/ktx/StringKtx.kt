package app.coinfo.library.core.ktx

fun String.toDoubleOrZero(): Double = this.toDoubleOrNull() ?: 0.0
