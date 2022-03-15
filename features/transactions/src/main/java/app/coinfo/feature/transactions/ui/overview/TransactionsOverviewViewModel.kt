package app.coinfo.feature.transactions.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.transactions.R
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsOverviewViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    val transactions: LiveData<List<UITransactionItem>>
        get() = _transactions
    private val _transactions = MutableLiveData(emptyList<UITransactionItem>())

    fun loadTransactions(portfolioId: Long, coinId: String) {
        viewModelScope.launch {
            _transactions.value = portfoliosRepository.loadTransactions(portfolioId, coinId).map { transaction ->
                UITransactionItem(
                    id = transaction.id,
                    date = transaction.date.toString(),
                    typeName = transaction.type.name,
                    typeImage = if (transaction.type == TransactionType.SELL)
                        R.drawable.transactions_ic_sell else R.drawable.transactions_ic_buy,
                    amount = transaction.amount.toString(),
                    worth = (transaction.amount * transaction.price).toString()
                )
            }
        }
    }
}
