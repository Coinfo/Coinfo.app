package app.coinfo.feature.transactions.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            _transactions.value = portfoliosRepository.loadTransactions(portfolioId, coinId).map {
                UITransactionItem(it.id)
            }
        }
    }
}
