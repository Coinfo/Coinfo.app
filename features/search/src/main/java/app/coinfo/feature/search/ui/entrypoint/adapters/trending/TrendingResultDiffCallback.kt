package app.coinfo.feature.search.ui.entrypoint.adapters.trending

import androidx.recyclerview.widget.DiffUtil

internal class TrendingResultDiffCallback : DiffUtil.ItemCallback<UITrendingItem>() {

    override fun areItemsTheSame(oldItem: UITrendingItem, newItem: UITrendingItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UITrendingItem, newItem: UITrendingItem) = oldItem == newItem
}
