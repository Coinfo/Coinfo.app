package app.coinfo.portfolios.ui.details.asset.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.portfolios.R
import app.coinfo.portfolios.model.UITransaction

class AssetTransactionAdapter :
    ListAdapter<UITransaction, AssetTransactionAdapter.AssetTransactionViewHolder>(DiffCallback()) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AssetTransactionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset_transaction, parent, false)
    )

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     *
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: AssetTransactionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class AssetTransactionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val transactionAmount: TextView = view.findViewById(R.id.text_view_transaction_amount)
        private val transactionPrice: TextView = view.findViewById(R.id.text_view_transaction_price)

        fun bind(transaction: UITransaction) {
            transactionAmount.text = transaction.amount.toString()
            transactionPrice.text = transaction.price.toString()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<UITransaction>() {
        override fun areItemsTheSame(oldItem: UITransaction, newItem: UITransaction) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: UITransaction, newItem: UITransaction) =
            oldItem == newItem
    }
}
