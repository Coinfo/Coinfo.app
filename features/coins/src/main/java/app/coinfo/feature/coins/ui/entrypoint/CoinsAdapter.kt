package app.coinfo.feature.coins.ui.entrypoint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemCoinBinding
import app.coinfo.feature.coins.model.CoinListItem

internal class CoinsAdapter : ListAdapter<CoinListItem, CoinsAdapter.ViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: ListItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinListItem) {
            binding.textView.text = coin.id.toString()
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCoinBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

internal class CoinsDiffCallback : DiffUtil.ItemCallback<CoinListItem>() {

    override fun areItemsTheSame(oldItem: CoinListItem, newItem: CoinListItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CoinListItem, newItem: CoinListItem) = oldItem == newItem
}
