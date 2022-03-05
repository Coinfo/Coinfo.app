package app.coinfo.feature.transactions.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.repository.coins.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertTransactionViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository
) : ViewModel() {

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

    val isNotesManuallyChanged: LiveData<Boolean>
        get() = _isNotesManuallyChanged
    private val _isNotesManuallyChanged = MutableLiveData(false)

    val symbol: LiveData<String>
        get() = _symbol
    private val _symbol = MutableLiveData("")

    fun loadCoinInformation(id: String) {
        viewModelScope.launch {
            val data = coinsRepository.getCoinData(id)
            _price.value = data.getCurrentPrice(Currency.EUR).toStringWithSuffix(2)
            _symbol.value = data.symbol
        }
    }

    fun onUpdatePrice(price: Double) {
        _price.value = price.toStringWithSuffix(2)
        _isPriceManuallyChanged.value = true
    }

    fun onUpdateFee(value: Double) {
        fee = value.toStringWithSuffix(2)
        _isFeeManuallyChanged.value = true
    }

    fun onUpdateNotes(value: String) {
        notes = value
        _isNotesManuallyChanged.value = true
    }

    fun getPriceValue() = _price.value ?: "0.0"
}
