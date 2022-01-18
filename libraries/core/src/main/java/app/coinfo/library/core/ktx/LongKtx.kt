package app.coinfo.library.core.ktx

import java.text.SimpleDateFormat
import java.util.Locale

private const val DEFAULT_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss"

/** Converts Long to Date string. */
fun Long.toDate(
    format: String = DEFAULT_DATE_FORMAT,
) = SimpleDateFormat(format, Locale.getDefault()).format(this)
