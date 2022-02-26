package app.coinfo.feature.coins.ui.entrypoint

import androidx.recyclerview.widget.DiffUtil
import app.coinfo.feature.coins.model.UICoinItem

internal class CoinsDiffCallback : DiffUtil.ItemCallback<UICoinItem>() {

    override fun areItemsTheSame(oldItem: UICoinItem, newItem: UICoinItem) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: UICoinItem, newItem: UICoinItem) = oldItem == newItem
}
