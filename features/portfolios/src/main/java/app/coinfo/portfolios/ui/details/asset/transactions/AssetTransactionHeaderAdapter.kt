package app.coinfo.portfolios.ui.details.asset.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.library.core.utils.Currency
import app.coinfo.portfolios.R
import app.coinfo.portfolios.model.UITransactionOverview

class AssetTransactionHeaderAdapter : RecyclerView.Adapter<AssetTransactionHeaderAdapter.HeaderViewHolder>() {

    private var overview: UITransactionOverview? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset_transaction_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = NUMBER_OF_ITEMS

    fun setOverview(transactionOverview: UITransactionOverview) {
        overview = transactionOverview
        notifyItemChanged(0)
    }

    inner class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val textViewTotalHolding: TextView = view.findViewById(R.id.text_view_total_holding)
        private val textViewBuy: TextView = view.findViewById(R.id.text_view_buy)
        private val textViewSell: TextView = view.findViewById(R.id.text_view_sell)
        private val textViewReward: TextView = view.findViewById(R.id.text_view_reward)
        private val textViewTotalWorth: TextView = view.findViewById(R.id.text_view_total_worth)

        fun bind() {
            textViewTotalHolding.setHoldings(overview)
            textViewTotalWorth.setTotalWorth(overview)
            textViewBuy.setBuying(overview)
            textViewSell.setSelling(overview)
            textViewReward.setReward(overview)
        }
    }

    private fun TextView.setHoldings(overview: UITransactionOverview?) = overview?.let {
        text = context.getString(R.string.placeholder_total_holdings, it.totalHoldings, it.assetID)
    }

    private fun TextView.setTotalWorth(overview: UITransactionOverview?) = overview?.let {
        text = context.getString(R.string.placeholder_total_worth, overview.totalWorth, Currency.toSymbol(it.currency))
    }

    private fun TextView.setBuying(overview: UITransactionOverview?) = overview?.let {
        text = context.getString(
            R.string.placeholder_total_and_average_buy,
            it.totalBoughtAmount, it.assetID, it.boughtAverage, Currency.toSymbol(it.currency)
        )
    }

    private fun TextView.setSelling(overview: UITransactionOverview?) = overview?.let {
        this.text = context.getString(
            R.string.placeholder_total_and_average_sell,
            it.totalSoldAmount, it.assetID, it.soldAverage, Currency.toSymbol(it.currency)
        )
    }

    private fun TextView.setReward(overview: UITransactionOverview?) = overview?.let {
        this.text = context.getString(
            R.string.placeholder_total_reward,
            it.totalRewardAmount, it.assetID, it.totalRewardInCash, Currency.toSymbol(it.currency)
        )
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 1
    }
}
