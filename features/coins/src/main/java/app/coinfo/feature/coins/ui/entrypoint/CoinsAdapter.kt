package app.coinfo.feature.coins.ui.entrypoint

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.R
import app.coinfo.feature.coins.databinding.ListItemCoinBinding
import app.coinfo.feature.coins.model.CoinListItem
import com.bumptech.glide.Glide

internal class CoinsAdapter : ListAdapter<CoinListItem, CoinsAdapter.ViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: ListItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinListItem) {
            binding.textViewCoinName.text = coin.name
            binding.textViewCoinPrice.text = coin.price
            binding.textViewCoinSymbol.text = coin.symbol
            binding.textViewMarketCapRank.text = coin.rank
            binding.textViewPriceChange.text = coin.priceChangePercentage24h
            binding.imageViewPriceChange.setImageResource(
                if (coin.isPriceChnage24hUp) R.drawable.coins_ic_arrow_up else R.drawable.coins_ic_arrow_down
            )
            binding.textViewMarketCap.text = coin.marketCap
            binding.imageViewCoinImage.load(coin.image)
            binding.executePendingBindings()
        }

        private fun ImageView.load(imageAddress: String) {
            Glide.with(this)
                .load(imageAddress)
                .into(this)
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
