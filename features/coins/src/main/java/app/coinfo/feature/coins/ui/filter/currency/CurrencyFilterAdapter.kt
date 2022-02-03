package app.coinfo.feature.coins.ui.filter.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemCurrencyBinding

class CurrencyFilterAdapter(
    private val onFilterClickListener: (CurrencyFilterItem) -> Unit
) : RecyclerView.Adapter<CurrencyFilterAdapter.ViewHolder>() {

    private var selectedFilter: CurrencyFilterItem? = null
    private val filters = CurrencyFilterItem.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        holder.bind(filter, filter == selectedFilter, onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    fun setPreselectedFilter(filter: CurrencyFilterItem) {
        selectedFilter = filter
    }

    class ViewHolder private constructor(
        private val binding: ListItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            filter: CurrencyFilterItem,
            isSelected: Boolean,
            onFilterClickListener: (CurrencyFilterItem) -> Unit
        ) {
            binding.textViewFilterName.setText(filter.resId)
            binding.imageViewChecked.isInvisible = !isSelected
            binding.root.setOnClickListener { onFilterClickListener(filter) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
