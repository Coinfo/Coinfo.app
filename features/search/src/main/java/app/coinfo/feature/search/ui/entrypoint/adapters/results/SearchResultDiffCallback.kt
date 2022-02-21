package app.coinfo.feature.search.ui.entrypoint.adapters.results

import androidx.recyclerview.widget.DiffUtil

internal class SearchResultDiffCallback : DiffUtil.ItemCallback<UISearchResult>() {

    override fun areItemsTheSame(oldItem: UISearchResult, newItem: UISearchResult) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: UISearchResult, newItem: UISearchResult) = oldItem == newItem
}
