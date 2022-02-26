package app.coinfo.feature.search.binders

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.search.ui.entrypoint.adapters.results.SearchResultsAdapter
import app.coinfo.feature.search.ui.entrypoint.adapters.results.UISearchItem
import app.coinfo.feature.search.ui.entrypoint.adapters.trending.TrendingResultsAdapter
import app.coinfo.feature.search.ui.entrypoint.adapters.trending.UITrendingItem

@BindingAdapter("searchResults")
internal fun bindSearchResults(recyclerView: RecyclerView, results: List<UISearchItem>?) {
    with(recyclerView.adapter as SearchResultsAdapter) {
        submitList(results)
    }
}

@BindingAdapter("trendingResults")
internal fun bindTrendingResults(recyclerView: RecyclerView, results: List<UITrendingItem>?) {
    with(recyclerView.adapter as TrendingResultsAdapter) {
        submitList(results)
    }
}

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}
