package app.coinfo.feature.transactions.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.transactions.R
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.toFormattedDate
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionOverviewViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    val transactionType: LiveData<Int>
        get() = _transactionType
    private val _transactionType = MutableLiveData(R.string.transactions_placeholder_text_transaction_type_undefined)

    val transactionDate: LiveData<String>
        get() = _transactionDate
    private val _transactionDate = MutableLiveData("")

    val transactionAmount: LiveData<Double>
        get() = _transactionAmount
    private val _transactionAmount = MutableLiveData(0.0)

    val transactionFee: LiveData<Double>
        get() = _transactionFee
    private val _transactionFee = MutableLiveData(0.0)

    val transactionCoinSymbol: LiveData<String>
        get() = _transactionCoinSymbol
    private val _transactionCoinSymbol = MutableLiveData("")

    val transactionCurrency: LiveData<Currency>
        get() = _transactionCurrency
    private val _transactionCurrency = MutableLiveData(Currency.NA)

    fun loadTransaction(id: Long) {
        viewModelScope.launch {
            with(portfoliosRepository.loadTransaction(id)) {
                _transactionType.value = type.resId
                _transactionDate.value = date.toFormattedDate()
                _transactionAmount.value = amount
                _transactionCoinSymbol.value = symbol
                _transactionCurrency.value = currency
                _transactionFee.value = fee
            }
        }
    }
}