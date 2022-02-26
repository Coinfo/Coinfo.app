package app.coinfo.feature.portfolios.ui.entrypoint

import androidx.recyclerview.widget.DiffUtil

internal class PortfoliosDiffCallback : DiffUtil.ItemCallback<UIPortfolioItem>() {

    override fun areItemsTheSame(oldItem: UIPortfolioItem, newItem: UIPortfolioItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UIPortfolioItem, newItem: UIPortfolioItem) = oldItem == newItem
}
