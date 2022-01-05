package app.coinfo.portfolios.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.R
import app.coinfo.portfolios.model.UIPortfolio
import app.coinfo.portfolios.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PortfolioAdapter(
    private val repository: Repository,
    private val logger: Logger,
) : ListAdapter<UIPortfolio, PortfolioAdapter.PhotosViewHolder>(DiffCallback()) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Called by RecyclerView when it starts observing this Adapter.
     * <p>
     * Keep in mind that same adapter may be observed by multiple RecyclerViews.
     *
     * @param recyclerView The RecyclerView instance which started observing this adapter.
     * @see #onDetachedFromRecyclerView(RecyclerView)
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        logger.logDebug(TAG, "Portfolio Adapter attached to the RecyclerView")
        coroutineScope.launch {
            repository
                .loadPortfolios()
                .catch { exception -> logger.logError(TAG, "Exception while loading profiles", exception) }
                .collect { submitList(it) }
        }
    }

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotosViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_portfolio, parent, false)
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
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    /**
     * Called by RecyclerView when it stops observing this Adapter.
     *
     * @param recyclerView The RecyclerView instance which stopped observing this adapter.
     * @see #onAttachedToRecyclerView(RecyclerView)
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        logger.logDebug(TAG, "Portfolio Adapter detached from the RecyclerView")
        coroutineScope.coroutineContext.cancelChildren()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.findViewById(R.id.text_view_portfolio_name)

        fun bind(item: UIPortfolio) {
            textViewName.text = item.displayName
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<UIPortfolio>() {
        override fun areItemsTheSame(oldItem: UIPortfolio, newItem: UIPortfolio) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UIPortfolio, newItem: UIPortfolio) =
            oldItem == newItem
    }

    companion object {

        private const val TAG = "PORT/PortfolioAdapter"
    }
}
