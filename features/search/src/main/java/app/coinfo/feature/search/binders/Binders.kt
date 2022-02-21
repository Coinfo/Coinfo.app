package app.coinfo.feature.search.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.search.ui.entrypoint.adapters.results.SearchResultsAdapter
import app.coinfo.feature.search.ui.entrypoint.adapters.results.UISearchResult

@BindingAdapter("searchResults")
internal fun bindSearchResults(recyclerView: RecyclerView, coins: List<UISearchResult>?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = SearchResultsAdapter()
    }
    (recyclerView.adapter as SearchResultsAdapter).submitList(coins)
}
