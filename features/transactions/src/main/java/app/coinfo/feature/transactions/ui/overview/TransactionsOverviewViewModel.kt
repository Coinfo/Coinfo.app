package app.coinfo.feature.transactions.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.transactions.R
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.toString
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
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
                    date = transaction.formattedDate,
                    typeName = transaction.type.resId,
                    typeImage = transaction.typeAsImage,
                    amount = transaction.formattedAmount,
                    worth = transaction.formattedWorth
                )
            }
        }
    }

    private val Transaction.formattedWorth
        get() = "${currency.symbol}${(amount * price).toString(2)}"

    private val Transaction.formattedAmount
        get() = "$amount ${symbol.uppercase()}"

    private val Transaction.formattedDate
        get() = SimpleDateFormat(TRANSACTION_DATE_FORMATTER, Locale.getDefault()).format(date)

    private val Transaction.typeAsImage
        get() = when (type) {
            TransactionType.SELL -> R.drawable.transactions_ic_sell
            TransactionType.BUY -> R.drawable.transactions_ic_buy
        }

    companion object {
        private const val TRANSACTION_DATE_FORMATTER = "EEE, d MMM yyyy HH:mm:ss"
    }
}
