package app.coinfo.feature.transactions.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.databinding.TransactionsListItemTransactionBinding

internal class TransactionsAdapter(
    private val onTransactionClickListener: (Long) -> Unit
) : ListAdapter<UITransactionItem, TransactionsAdapter.ViewHolder>(
    TransactionsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onTransactionClickListener)
    }

    class ViewHolder private constructor(
        private val binding: TransactionsListItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: UITransactionItem, onTransactionClickListener: (Long) -> Unit) {
            binding.root.setOnClickListener { onTransactionClickListener(transaction.id) }
            binding.imageViewTransactionType.setImageResource(transaction.typeImage)
            binding.textViewTransactionType.setText(transaction.typeName)
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
