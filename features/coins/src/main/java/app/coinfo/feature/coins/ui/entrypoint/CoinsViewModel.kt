package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    var timeIntervalValue = preferences.loadTimeInterval()
        set(value) {
            if (value == timeIntervalValue) return
            field = value
            _timeInterval.value = value
            preferences.saveTimeInterval(value)
            // Check if not same and then load
            loadCoins()
        }

    var currencyFilterValue = CurrencyFilterItem.fromValue(preferences.loadCurrency())
        set(value) {
            if (value == currencyFilterValue) return
            field = value
            _currencyFilter.value = value
            preferences.saveCurrency(value.value)
            // Check if not same and then load
            loadCoins()
        }

    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    private val _isRefreshing = MutableLiveData(false)

    val timeInterval: LiveData<TimeInterval>
        get() = _timeInterval
    private val _timeInterval = MutableLiveData(timeIntervalValue)

    val currencyFilter: LiveData<CurrencyFilterItem>
        get() = _currencyFilter
    private val _currencyFilter = MutableLiveData(currencyFilterValue)

    val coins: LiveData<List<CoinListItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<CoinListItem>())

    init {
        loadCoins()
    }

    fun refreshCoins() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val job = loadCoins()
            job.join()
            _isRefreshing.value = false
        }
    }

    private fun loadCoins() = viewModelScope.launch {
        _coins.postValue(repository.loadCoins(currencyFilterValue, timeIntervalValue))
    }
}
