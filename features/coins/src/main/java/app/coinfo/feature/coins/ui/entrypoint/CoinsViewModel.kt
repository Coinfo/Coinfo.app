package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor() : ViewModel() {

    val coins: LiveData<List<CoinListItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<CoinListItem>())

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData(false)

    init {
        loadCoins()
    }

    private fun loadCoins() {
        _loading.value = true
        viewModelScope.launch {
            _coins.postValue(listOf(CoinListItem(1), CoinListItem(2)))
            _loading.value = false
        }
    }
}
