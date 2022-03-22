package app.coinfo.feature.transactions.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.ui.transactions.TransactionsAdapter
import app.coinfo.feature.transactions.ui.transactions.UITransactionItem
import app.coinfo.library.core.enums.TransactionType
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("transactions")
internal fun bindTransactions(recyclerView: RecyclerView, portfolios: List<UITransactionItem>?) {
    (recyclerView.adapter as TransactionsAdapter).submitList(portfolios)
}

@BindingAdapter("transactionType")
internal fun bindTransactionType(toggleGroup: MaterialButtonToggleGroup, type: TransactionType) {
    toggleGroup.check(
        when (type) {
            TransactionType.SELL -> R.id.button_sell
            TransactionType.BUY -> R.id.button_buy
        }
    )
}
