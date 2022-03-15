package app.coinfo.feature.transactions.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.databinding.TransactionsListItemTransactionBinding

internal class TransactionsAdapter : ListAdapter<UITransactionItem, TransactionsAdapter.ViewHolder>(
    TransactionsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: TransactionsListItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: UITransactionItem) {
            binding.imageViewTransactionType.setImageResource(transaction.typeImage)
            binding.textViewTransactionType.text = transaction.typeName
            binding.textViewTransactionDate.text = transaction.date
            binding.textViewTransactionAmount.text = transaction.amount
            binding.textViewTransactionWorth.text = transaction.worth
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionsListItemTransactionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
