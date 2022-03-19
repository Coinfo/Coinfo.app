package app.coinfo.library.core.ktx

import java.text.SimpleDateFormat
import java.util.Locale

/** Private */
private const val TRANSACTION_DATE_FORMATTER = "EEE, d MMM yyyy HH:mm:ss"

fun Long.toFormattedDate(format: String = TRANSACTION_DATE_FORMATTER) =
    SimpleDateFormat(TRANSACTION_DATE_FORMATTER, Locale.getDefault()).format(this)
