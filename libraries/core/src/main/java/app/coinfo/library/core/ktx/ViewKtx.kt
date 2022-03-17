package app.coinfo.library.core.ktx

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setString(@StringRes resId: Int, vararg formatArgs: Any?) {
    text = context.getString(resId, *formatArgs)
}
