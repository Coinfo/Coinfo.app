package app.coinfo.feature.portfolios.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.portfolios.ui.entrypoint.PortfoliosAdapter
import app.coinfo.feature.portfolios.ui.entrypoint.UIPortfolioItem

@BindingAdapter("portfolios")
internal fun bindPortfolios(recyclerView: RecyclerView, portfolios: List<UIPortfolioItem>?) {
    (recyclerView.adapter as PortfoliosAdapter).submitList(portfolios)
}
