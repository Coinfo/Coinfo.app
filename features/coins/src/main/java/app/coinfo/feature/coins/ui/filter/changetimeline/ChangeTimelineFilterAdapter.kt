package app.coinfo.feature.coins.ui.filter.changetimeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemChangeTimelineBinding

class ChangeTimelineFilterAdapter(
    private val onFilterClickListener: (ChangeTimelineFilterItem) -> Unit
) : RecyclerView.Adapter<ChangeTimelineFilterAdapter.ViewHolder>() {

    private val filters = ChangeTimelineFilterItem.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filters[position], onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    class ViewHolder private constructor(
        private val binding: ListItemChangeTimelineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: ChangeTimelineFilterItem, onFilterClickListener: (ChangeTimelineFilterItem) -> Unit) {
            binding.textViewFilterName.setText(filter.resId)
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
