package app.coinfo.feature.coins.ui.filter.changetimeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemChangeTimelineBinding
import app.coinfo.library.core.enums.TimeInterval

class ChangeTimelineFilterAdapter(
    private val onFilterClickListener: (TimeInterval) -> Unit
) : RecyclerView.Adapter<ChangeTimelineFilterAdapter.ViewHolder>() {

    private var selectedFilter: TimeInterval? = null
    private val filters = TimeInterval.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        holder.bind(filter, filter == selectedFilter, onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    fun setPreselectedFilter(filter: TimeInterval) {
        selectedFilter = filter
    }

    class ViewHolder private constructor(
        private val binding: ListItemChangeTimelineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            filter: TimeInterval,
            isSelected: Boolean,
            onFilterClickListener: (TimeInterval) -> Unit
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
