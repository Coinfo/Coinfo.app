/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.feature.coins.ui.entrypoint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.R
import app.coinfo.feature.coins.databinding.CoinsListItemCoinBinding
import app.coinfo.feature.coins.model.UICoinItem
import com.bumptech.glide.Glide

internal class CoinsAdapter : ListAdapter<UICoinItem, CoinsAdapter.ViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: CoinsListItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: UICoinItem) {
            binding.root.setOnClickListener { navigateToDeepLink(it, coin.id) }
            binding.textViewCoinName.text = coin.name
            binding.textViewCoinPrice.text = coin.price
            binding.textViewCoinSymbol.text = coin.symbol
            binding.textViewMarketCapRank.text = coin.rank
            binding.textViewPriceChange.text = coin.priceChangePercentage
            binding.imageViewPriceChange.setImageResource(
                if (coin.isPriceChangeUp) R.drawable.coins_ic_arrow_up else R.drawable.coins_ic_arrow_down
            )
            binding.textViewMarketCap.text = coin.marketCap
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
                val binding = CoinsListItemCoinBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
