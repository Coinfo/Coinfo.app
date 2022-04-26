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

package app.coinfo.feature.portfolios.ui.entrypoint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.portfolios.databinding.PortfoliosListItemPortfolioBinding

internal class PortfoliosAdapter(
    private val onAddAssetClickListener: (portfolioId: Long) -> Unit
) : ListAdapter<UIPortfolioItem, PortfoliosAdapter.ViewHolder>(
    PortfoliosDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onAddAssetClickListener)
    }

    class ViewHolder private constructor(
        private val binding: PortfoliosListItemPortfolioBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(portfolio: UIPortfolioItem, onAddAssetClickListener: (portfolioId: Long) -> Unit) = with(portfolio) {
            binding.root.setOnClickListener { navigateToPortfolioDetails(it, id) }
            binding.buttonPortfolioEdit.setOnClickListener { navigateToEditPortfolio(it, id) }
            binding.buttonPortfolioAddAsset.setOnClickListener { onAddAssetClickListener(id) }
            binding.textViewPortfolioName.text = name
            binding.textViewPortfolioWorth.text = worth
            binding.textViewPortfolioProfitLoss.text = totalProfitLoss
            binding.textViewPortfolioProfitLossPercentage.text = totalProfitLossPercentage
            binding.textViewPortfolioProfitLossPercentage.setTextColor(
                ContextCompat.getColor(binding.root.context, trendColor)
            )
            binding.imageViewPortfolioProfitLossTrend.setImageResource(trendImageRes)
            binding.executePendingBindings()
        }

        private fun navigateToPortfolioDetails(view: View, id: Long) = view.findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/portfolio?id=$id".toUri())
                .build()
        )

        private fun navigateToEditPortfolio(view: View, id: Long) = view.findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/portfolios?portfolioId=$id".toUri())
                .build()
        )

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PortfoliosListItemPortfolioBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
