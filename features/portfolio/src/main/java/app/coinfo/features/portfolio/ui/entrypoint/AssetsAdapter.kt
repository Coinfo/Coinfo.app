package app.coinfo.features.portfolio.ui.entrypoint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.features.portfolio.databinding.PortfolioListItemAssetBinding

internal class AssetsAdapter : ListAdapter<UIAssetsItem, AssetsAdapter.ViewHolder>(
    AssetsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: PortfolioListItemAssetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asset: UIAssetsItem) {
            binding.textView.text = asset.id
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PortfolioListItemAssetBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
