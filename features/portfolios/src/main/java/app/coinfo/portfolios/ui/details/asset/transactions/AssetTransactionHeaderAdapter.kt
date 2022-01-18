package app.coinfo.portfolios.ui.details.asset.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.portfolios.R

class AssetTransactionHeaderAdapter : RecyclerView.Adapter<AssetTransactionHeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset_transaction_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) = Unit

    override fun getItemCount() = NUMBER_OF_ITEMS

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val NUMBER_OF_ITEMS = 1
    }
}
