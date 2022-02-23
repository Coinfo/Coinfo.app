package app.coinfo.feature.portfolios.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

/** Shows [Snackbar] with given [message] and [action] */
internal fun View.shortSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}
