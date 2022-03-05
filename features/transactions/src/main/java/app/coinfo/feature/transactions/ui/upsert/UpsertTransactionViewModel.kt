package app.coinfo.feature.transactions.ui.upsert

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
}
