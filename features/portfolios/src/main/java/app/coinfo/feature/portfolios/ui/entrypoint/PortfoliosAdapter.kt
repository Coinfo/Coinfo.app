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

internal class PortfoliosAdapter : ListAdapter<UIPortfolioItem, PortfoliosAdapter.ViewHolder>(
    PortfoliosDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: PortfoliosListItemPortfolioBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(portfolio: UIPortfolioItem) = with(portfolio) {
            binding.root.setOnClickListener { navigateToPortfolioDetails(it, id) }
            binding.buttonPortfolioEdit.setOnClickListener { navigateToEditPortfolio(it, id) }
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
