package app.coinfo.features.portfolio.ui.entrypoint

import androidx.recyclerview.widget.DiffUtil

internal class AssetsDiffCallback : DiffUtil.ItemCallback<UIAssetsItem>() {

    override fun areItemsTheSame(oldItem: UIAssetsItem, newItem: UIAssetsItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UIAssetsItem, newItem: UIAssetsItem) = oldItem == newItem
}
