package app.coinfo.feature.transactions.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.toDoubleOrZero
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertTransactionViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val portfoliosRepository: PortfoliosRepository,
) : ViewModel() {

    private var coinId: String? = null
    private var portfolioId: Long? = null
    private var amount: Double = 0.0

    val price: LiveData<String>
        get() = _price
    private val _price = MutableLiveData("")

    val isPriceManuallyChanged: LiveData<Boolean>
        get() = _isPriceManuallyChanged
    private val _isPriceManuallyChanged = MutableLiveData(false)

    var fee: String = ""
        private set

    val isFeeManuallyChanged: LiveData<Boolean>
        get() = _isFeeManuallyChanged
    private val _isFeeManuallyChanged = MutableLiveData(false)

    var notes: String = ""
        private set

    val currency: LiveData<Currency>
        get() = _currency
    private val _currency = MutableLiveData(Currency.EUR)

    val isCurrencyManuallyChanged: LiveData<Boolean>
        get() = _isCurrencyManuallyChanged
    private val _isCurrencyManuallyChanged = MutableLiveData(false)

    val isNotesManuallyChanged: LiveData<Boolean>
        get() = _isNotesManuallyChanged
    private val _isNotesManuallyChanged = MutableLiveData(false)

    val symbol: LiveData<String>
        get() = _symbol
    private val _symbol = MutableLiveData("")

    fun loadCoinInformation(coinId: String, portfolioId: Long) {
        this.coinId = coinId
        this.portfolioId = portfolioId
        viewModelScope.launch {
            val data = coinsRepository.getCoinData(coinId)
            _price.value = data.getCurrentPrice(Currency.EUR).toStringWithSuffix(2)
            _symbol.value = data.symbol
        }
    }

    fun onUpdatePrice(price: Double) {
        _price.value = price.toStringWithSuffix(2)
        _isPriceManuallyChanged.value = true
    }

    fun onAmountChanged(s: CharSequence) {
        amount = s.toString().toDoubleOrZero()
    }

    fun onUpdateFee(value: Double) {
        fee = value.toStringWithSuffix(2)
        _isFeeManuallyChanged.value = true
    }

    fun onUpdateNotes(value: String) {
        notes = value
        _isNotesManuallyChanged.value = true
    }

    fun onUpdateCurrency(value: Currency) {
        _currency.value = value
        _isCurrencyManuallyChanged.value = true
    }

    fun onAddTransaction() {
        viewModelScope.launch {
            portfoliosRepository.addTransaction(
                Transaction(
                    coinId = coinId!!,
                    portfolioId = portfolioId!!,
                    symbol = _symbol.value!!,
                    amount = amount,
                    price = _price.value?.toDoubleOrZero() ?: 0.0,
                    fee = fee.toDoubleOrZero(),
                    currency = _currency.value!!
                )
            )
        }
    }

    fun getPriceValue() = _price.value ?: "0.0"
}
