package app.coinfo.portfolios.ui.details.asset.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.library.core.utils.Currency
import app.coinfo.portfolios.R
import app.coinfo.portfolios.model.UITransaction
import app.coinfo.portfolios.model.UITransactionType

class AssetTransactionHeaderAdapter : RecyclerView.Adapter<AssetTransactionHeaderAdapter.HeaderViewHolder>() {

    private var assetId: String = ""
    private var totalHolding: Double = 0.0
    private var totalWorth: Double = 0.0
    private var currencySymbol: String = Currency.NA.symbol
    private var totalBuyPrice: Double = 0.0
    private var totalBuyHolding: Double = 0.0
    private var totalBuyPricePerAsset: Double = 0.0
    private var buyCount = 0
    private var buyAvg = 0.0
    private var totalSellPrice: Double = 0.0
    private var totalSellHolding: Double = 0.0
    private var totalSellPricePerAsset: Double = 0.0
    private var sellCount = 0
    private var sellAvg = 0.0
    private var totalInterestEarnHolding: Double = 0.0
    private var totalInterestEarnPrice: Double = 0.0
    private var totalRewardHolding: Double = 0.0
    private var totalRewardPrice: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset_transaction_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = NUMBER_OF_ITEMS

    fun setTransactions(transactions: List<UITransaction>?) {
        transactions?.forEachIndexed { index, transaction ->
            // Values which should be set only once.
            if (index == 0) {
                assetId = transaction.assetId
                currencySymbol = Currency.toSymbol(transaction.currency)
            }
            totalHolding += transaction.amount
            when (transaction.transactionType) {
                UITransactionType.BUY -> {
                    buyCount++
                    totalBuyHolding += transaction.amount
                    totalBuyPricePerAsset += (transaction.price / transaction.amount)
                    totalBuyPrice += transaction.price
                }
                UITransactionType.SELL -> {
                    sellCount++
                    totalSellHolding += transaction.amount
                    totalSellPricePerAsset += (transaction.price / transaction.amount)
                    totalSellPrice += transaction.price
                }
                UITransactionType.INTEREST_EARN -> {
                    totalInterestEarnHolding += transaction.amount
                    totalInterestEarnPrice += transaction.price
                }
                UITransactionType.STAKE_REWARD -> {
                    totalRewardHolding += transaction.amount
                    totalRewardPrice += transaction.price
                }
            }

            if (index == transactions.size - 1) {
                buyAvg = totalBuyPricePerAsset / buyCount
                sellAvg = totalSellPricePerAsset / sellCount
                totalWorth = totalSellPrice - totalBuyPrice
            }
        }
        notifyItemChanged(0)
    }

    inner class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val textViewTotalHolding: TextView = view.findViewById(R.id.text_view_total_holding)
        private val textViewTotalWorth: TextView = view.findViewById(R.id.text_view_total_worth)
        private val textViewBuy: TextView = view.findViewById(R.id.text_view_buy)
        private val textViewSell: TextView = view.findViewById(R.id.text_view_sell)
        private val textViewInterestEarn: TextView = view.findViewById(R.id.text_view_interest_earn)
        private val textViewReward: TextView = view.findViewById(R.id.text_view_reward)

        fun bind() {
            val context = view.context
            textViewTotalHolding.text = context.getString(R.string.placeholder_total_holdings, totalHolding, assetId)
            textViewTotalWorth.text = context.getString(R.string.placeholder_total_worth, totalWorth, currencySymbol)
            textViewBuy.text = context.getString(
                R.string.placeholder_total_and_average_buy,
                totalBuyHolding, assetId, buyAvg, currencySymbol
            )
            textViewSell.text = context.getString(
                R.string.placeholder_total_and_average_sell,
                totalSellHolding, assetId, sellAvg, currencySymbol
            )
            textViewInterestEarn.text = context.getString(
                R.string.placeholder_total_interest_earn,
                totalInterestEarnPrice, currencySymbol, totalInterestEarnHolding, assetId
            )
            textViewReward.text = context.getString(
                R.string.placeholder_total_reward,
                totalRewardPrice, currencySymbol, totalRewardHolding, assetId
            )
        }
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 1
    }
}
