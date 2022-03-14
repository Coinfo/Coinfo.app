package app.coinfo.features.portfolio.ui.entrypoint

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.features.portfolio.databinding.PortfolioListItemAssetBinding
import com.bumptech.glide.Glide

internal class AssetsAdapter(
    private val onAssetClickListener: (String) -> Unit
) : ListAdapter<UIAssetsItem, AssetsAdapter.ViewHolder>(
    AssetsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent, onAssetClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: PortfolioListItemAssetBinding,
        private val onAssetClickListener: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asset: UIAssetsItem) {
            binding.root.setOnClickListener { onAssetClickListener(asset.id) }
            binding.textViewAssetName.text = asset.name
            binding.textViewAssetSymbol.text = asset.symbol
            binding.textViewAssetPrice.text = asset.price
            binding.imageViewAssetIcon.load(asset.icon)
            binding.textViewAssetTotalHoldings.text = asset.totalHoldings
            binding.textViewAssetAllTimeProfitLose.text = asset.totalProfitLoss
            binding.textViewAssetTotalPrice.text = asset.totalPrice
            binding.executePendingBindings()
        }

        private fun ImageView.load(imageAddress: String) {
            Glide.with(this)
                .load(imageAddress)
                .into(this)
        }

        companion object {
            fun from(parent: ViewGroup, onAssetClickListener: (String) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PortfolioListItemAssetBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onAssetClickListener)
            }
        }
    }
}
