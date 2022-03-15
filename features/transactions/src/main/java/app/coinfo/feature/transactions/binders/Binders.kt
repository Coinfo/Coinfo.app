package app.coinfo.feature.transactions.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.ui.overview.TransactionsAdapter
import app.coinfo.feature.transactions.ui.overview.UITransactionItem

@BindingAdapter("transactions")
internal fun bindTransactions(recyclerView: RecyclerView, portfolios: List<UITransactionItem>?) {
    (recyclerView.adapter as TransactionsAdapter).submitList(portfolios)
}
