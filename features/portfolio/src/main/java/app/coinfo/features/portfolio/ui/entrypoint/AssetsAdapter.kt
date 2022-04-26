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

package app.coinfo.features.portfolio.ui.entrypoint

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.features.portfolio.databinding.PortfolioListItemAssetBinding
import com.bumptech.glide.Glide

internal class AssetsAdapter(
    private val onAssetClickListener: (String, String) -> Unit
) : ListAdapter<UIAssetsItem, AssetsAdapter.ViewHolder>(
    AssetsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent, onAssetClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: PortfolioListItemAssetBinding,
        private val onAssetClickListener: (String, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asset: UIAssetsItem) {
            val context = binding.root.context
            binding.root.setOnClickListener { onAssetClickListener(asset.id, asset.symbol) }
            binding.textViewAssetSymbol.text = asset.symbol
            binding.textViewAssetPrice.text = asset.price
            binding.imageViewAssetIcon.load(asset.icon)
            binding.textViewAssetTotalHoldings.text = asset.totalHoldings
            binding.textViewAssetAllTimeProfitLose.text = asset.totalProfitLoss
            binding.textViewAssetAllTimeProfitLosePercentage.text = asset.totalProfitLossPercentage
            binding.textViewAssetAllTimeProfitLosePercentage.setTextColor(
                ContextCompat.getColor(context, asset.color)
            )
            binding.textViewAssetTotalPrice.text = asset.totalPrice
            binding.imageViewProfitLossTrend.setImageDrawable(ContextCompat.getDrawable(context, asset.trend))
            binding.executePendingBindings()
        }

        private fun ImageView.load(imageAddress: String) {
            Glide.with(this)
                .load(imageAddress)
                .into(this)
        }

        companion object {
            fun from(parent: ViewGroup, onAssetClickListener: (String, String) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PortfolioListItemAssetBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onAssetClickListener)
            }
        }
    }
}
