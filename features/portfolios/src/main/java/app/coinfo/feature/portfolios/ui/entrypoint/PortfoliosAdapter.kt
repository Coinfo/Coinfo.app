package app.coinfo.feature.portfolios.ui.entrypoint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        fun bind(portfolio: UIPortfolioItem) {
            binding.root.setOnClickListener { navigateToDeepLink(it, portfolio.id) }
            binding.textViewPortfolioName.text = portfolio.name
            binding.executePendingBindings()
        }

        private fun navigateToDeepLink(view: View, id: Long) = view.findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/coin?id=$id".toUri())
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
