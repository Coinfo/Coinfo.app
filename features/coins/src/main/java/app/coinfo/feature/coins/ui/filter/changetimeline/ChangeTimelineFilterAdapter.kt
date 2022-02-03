package app.coinfo.feature.coins.ui.filter.changetimeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemChangeTimelineBinding

class ChangeTimelineFilterAdapter(
    private val onFilterClickListener: (ChangeTimelineFilterItem) -> Unit
) : RecyclerView.Adapter<ChangeTimelineFilterAdapter.ViewHolder>() {

    private var selectedFilter: ChangeTimelineFilterItem? = null
    private val filters = ChangeTimelineFilterItem.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        holder.bind(filter, filter == selectedFilter, onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    fun setPreselectedFilter(filter: ChangeTimelineFilterItem) {
        selectedFilter = filter
    }

    class ViewHolder private constructor(
        private val binding: ListItemChangeTimelineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            filter: ChangeTimelineFilterItem,
            isSelected: Boolean,
            onFilterClickListener: (ChangeTimelineFilterItem) -> Unit
        ) {
            binding.textViewFilterName.setText(filter.resId)
            binding.imageViewChecked.isInvisible = !isSelected
            binding.root.setOnClickListener { onFilterClickListener(filter) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemChangeTimelineBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
