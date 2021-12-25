package app.coinfo.fngindex.wigdet.ext

import android.view.View
import android.widget.RemoteViews
import androidx.annotation.IdRes

/** Hides the remote view */
internal fun RemoteViews.hide(@IdRes viewId: Int) =
    this.setViewVisibility(viewId, View.GONE)

/** Shows the remote view */
internal fun RemoteViews.show(@IdRes viewId: Int) =
    this.setViewVisibility(viewId, View.VISIBLE)
