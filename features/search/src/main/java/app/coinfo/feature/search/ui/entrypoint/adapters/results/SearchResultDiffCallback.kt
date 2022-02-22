package app.coinfo.feature.search.ui.entrypoint.adapters.results

import androidx.recyclerview.widget.DiffUtil

internal class SearchResultDiffCallback : DiffUtil.ItemCallback<UISearchItem>() {

    override fun areItemsTheSame(oldItem: UISearchItem, newItem: UISearchItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UISearchItem, newItem: UISearchItem) = oldItem == newItem
}
