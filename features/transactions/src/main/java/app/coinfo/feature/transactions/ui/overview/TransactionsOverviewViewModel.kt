package app.coinfo.feature.transactions.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.transactions.R
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.safeValue
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TransactionsOverviewViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val portfoliosRepository: PortfoliosRepository,
    preferences: Preferences,
) : ViewModel() {

    private var averageBuyPricePerCoin = 0.0
    private var averageBuyAmount = 0.0

    val transactions: LiveData<List<UITransactionItem>>
        get() = _transactions
    private val _transactions = MutableLiveData(emptyList<UITransactionItem>())

    val totalAmount: LiveData<Double>
        get() = _totalAmount
    private val _totalAmount = MutableLiveData(0.0)

    val totalWorth: LiveData<Double>
        get() = _totalWorth
    private val _totalWorth = MutableLiveData(0.0)

    val currency: LiveData<Currency>
        get() = _currency
    private val _currency = MutableLiveData(preferences.loadCurrency())

    val averageBuy: LiveData<Double>
        get() = _averageBuy
    private val _averageBuy = MutableLiveData(0.0)

    fun loadTransactions(portfolioId: Long, coinId: String) {
        viewModelScope.launch {
            val coin = coinsRepository.getCoinData(coinId)
            val transactions = portfoliosRepository.loadTransactions(portfolioId, coinId)
            _transactions.value = transactions.mapIndexed { index, transaction ->

                _totalAmount.value = _totalAmount.safeValue + transaction.amount
                _totalWorth.value = _totalWorth.safeValue +
                    (_totalAmount.safeValue * coin.getCurrentPrice(_currency.safeValue))

                calculateAverageBuyAndSell(transaction, coin)

                if (transactions.lastIndex == index) {
                    _averageBuy.value = averageBuyPricePerCoin / averageBuyAmount
                }

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
        get() = "${currency.symbol}${(amount * pricePerCoin).toString(2)}"

    private val Transaction.formattedAmount
        get() = "$amount ${symbol.uppercase()}"

    private val Transaction.formattedDate
        get() = SimpleDateFormat(TRANSACTION_DATE_FORMATTER, Locale.getDefault()).format(date)

    private val Transaction.typeAsImage
        get() = when (type) {
            TransactionType.SELL -> R.drawable.transactions_ic_sell
            TransactionType.BUY -> R.drawable.transactions_ic_buy
        }

    private fun calculateAverageBuyAndSell(transaction: Transaction, coinData: CoinData) {
        when (transaction.type) {
            TransactionType.BUY -> {
                averageBuyPricePerCoin += if (transaction.currency == _currency.safeValue) {
                    transaction.pricePerCoin
                } else {
                    val priceNativeCurrency = coinData.getCurrentPrice(transaction.currency)
                    val priceExpectCurrency = coinData.getCurrentPrice(_currency.safeValue)
                    (transaction.pricePerCoin * (priceExpectCurrency / priceNativeCurrency))
                }
                averageBuyAmount++
            }
        }
    }

    companion object {
        private const val TRANSACTION_DATE_FORMATTER = "EEE, d MMM yyyy HH:mm:ss"
    }
}
