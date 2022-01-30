package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.library.core.utils.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    val coins: LiveData<List<CoinListItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<CoinListItem>())

    init {
        loadCoins(Currency.EUR)
    }

    private fun loadCoins(currency: Currency) {
        viewModelScope.launch {
            _coins.postValue(repository.loadCoins(currency))
        }
    }
}
