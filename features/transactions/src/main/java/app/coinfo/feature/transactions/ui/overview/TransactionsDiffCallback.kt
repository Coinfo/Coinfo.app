package app.coinfo.feature.transactions.ui.overview

import androidx.recyclerview.widget.DiffUtil

internal class TransactionsDiffCallback : DiffUtil.ItemCallback<UITransactionItem>() {

    override fun areItemsTheSame(oldItem: UITransactionItem, newItem: UITransactionItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UITransactionItem, newItem: UITransactionItem) = oldItem == newItem
}
