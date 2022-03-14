package app.coinfo.features.portfolio.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.features.portfolio.ui.entrypoint.AssetsAdapter
import app.coinfo.features.portfolio.ui.entrypoint.UIAssetsItem

@BindingAdapter("assets")
internal fun bindPortfolios(recyclerView: RecyclerView, assets: List<UIAssetsItem>?) {
    (recyclerView.adapter as AssetsAdapter).submitList(assets)
}
