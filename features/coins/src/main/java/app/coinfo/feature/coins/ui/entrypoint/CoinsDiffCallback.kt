package app.coinfo.feature.coins.ui.entrypoint

import androidx.recyclerview.widget.DiffUtil
import app.coinfo.feature.coins.model.CoinListItem

internal class CoinsDiffCallback : DiffUtil.ItemCallback<CoinListItem>() {

    override fun areItemsTheSame(oldItem: CoinListItem, newItem: CoinListItem) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CoinListItem, newItem: CoinListItem) = oldItem == newItem
}
