package app.coinfo.fngindex.extension

import android.view.View
import android.widget.RemoteViews
import androidx.annotation.IdRes

/** Hides the remote view */
fun RemoteViews.hide(@IdRes viewId: Int) =
    this.setViewVisibility(viewId, View.INVISIBLE)

/** Shows the remote view */
fun RemoteViews.show(@IdRes viewId: Int) =
    this.setViewVisibility(viewId, View.VISIBLE)
