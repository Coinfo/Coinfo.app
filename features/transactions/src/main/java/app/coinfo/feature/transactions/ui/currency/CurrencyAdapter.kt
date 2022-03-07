package app.coinfo.feature.transactions.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.databinding.TransactionsListItemCurrencyBinding
import app.coinfo.library.core.enums.Currency

class CurrencyAdapter(
    private val onFilterClickListener: (Currency) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var selectedFilter: Currency? = null
    private val filters = Currency.values().filter { it.code != "N/A" }.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        holder.bind(filter, filter == selectedFilter, onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    fun setPreselectedFilter(filter: Currency) {
        selectedFilter = filter
    }

    class ViewHolder private constructor(
        private val binding: TransactionsListItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currency: Currency,
            isSelected: Boolean,
            onFilterClickListener: (Currency) -> Unit
        ) {
            binding.textViewFilterName.setText(currency.resId)
            binding.imageViewChecked.isInvisible = !isSelected
            binding.root.setOnClickListener { onFilterClickListener(currency) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionsListItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
