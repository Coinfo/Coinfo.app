package app.coinfo.feature.coins.binders

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.ui.entrypoint.CoinsAdapter

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("coins")
internal fun bindCoins(recyclerView: RecyclerView, coins: List<CoinListItem>?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = CoinsAdapter()
    }
    (recyclerView.adapter as CoinsAdapter).submitList(coins)
}
