package app.coinfo.feature.search.ui.entrypoint.adapters.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.search.databinding.SearchListItemTrendingBinding
import com.bumptech.glide.Glide

internal class TrendingResultsAdapter : ListAdapter<UITrendingItem, TrendingResultsAdapter.ViewHolder>(
    TrendingResultDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: SearchListItemTrendingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: UITrendingItem) {
            binding.root.setOnClickListener { navigateToDeepLink(it, coin.id) }
            binding.textViewCoinName.text = coin.name
            binding.textViewCoinSymbol.text = coin.symbol
            binding.textViewMarketCapRank.text = coin.rank
            binding.imageViewCoinImage.load(coin.image)
            binding.executePendingBindings()
        }

        private fun navigateToDeepLink(view: View, id: String) = view.findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/coin?id=$id".toUri())
                .build()
        )

        private fun ImageView.load(imageAddress: String) {
            Glide.with(this)
                .load(imageAddress)
                .into(this)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchListItemTrendingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
