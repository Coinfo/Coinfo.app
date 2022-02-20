package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.prefs.CoinsPreferences
import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository,
    private val preferences: Preferences,
    private val localPreferences: CoinsPreferences
) : ViewModel() {

    val isFavoritesModeActive: LiveData<Boolean>
        get() = _isFavoritesModeActive
    private val _isFavoritesModeActive = MutableLiveData(localPreferences.isFavoritesModeActive())

    val currentTimeInterval: TimeInterval
        get() = preferences.loadTimeInterval()

    val currentCurrency: Currency
        get() = preferences.loadCurrency()

    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    private val _isRefreshing = MutableLiveData(false)

    val timeInterval: LiveData<TimeInterval>
        get() = _timeInterval
    private val _timeInterval = MutableLiveData(currentTimeInterval)

    val currency: LiveData<Currency>
        get() = _currency
    private val _currency = MutableLiveData(currentCurrency)

    val coins: LiveData<List<CoinListItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<CoinListItem>())

    fun refreshCoins(showLoading: Boolean = false) {
        viewModelScope.launch {
            _isRefreshing.value = showLoading
            val job = loadCoins()
            job.join()
            _isRefreshing.value = false
        }
    }

    fun onTimeIntervalChanged(interval: TimeInterval) {
        _timeInterval.value = interval
        preferences.saveTimeInterval(interval)
        loadCoins()
    }

    fun onCurrencyChanged(currency: Currency) {
        _currency.value = currency
        preferences.saveCurrency(currency)
        loadCoins()
    }

    fun onFavoritesModeChanged() {
        val result = localPreferences.switchFavoritesMode()
        _isFavoritesModeActive.value = result
    }

    private fun loadCoins() = viewModelScope.launch {
        _timeInterval.value = currentTimeInterval
        _currency.value = currentCurrency
        _coins.value = repository.loadCoins(currentCurrency, currentTimeInterval)
    }
}
