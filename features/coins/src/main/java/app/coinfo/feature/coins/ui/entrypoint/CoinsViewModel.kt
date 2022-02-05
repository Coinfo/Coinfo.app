package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    var changeTimelineFilterValue = ChangeTimelineFilterItem.fromValue(preferences.loadChangeTimeline())
        set(value) {
            if (value == changeTimelineFilterValue) return
            field = value
            _changeTimelineFilter.value = value
            preferences.saveChangeTimeline(value.value)
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

    val changeTimelineFilter: LiveData<ChangeTimelineFilterItem>
        get() = _changeTimelineFilter
    private val _changeTimelineFilter = MutableLiveData(changeTimelineFilterValue)

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
        _isRefreshing.value = true
        loadCoins()
        _isRefreshing.value = false
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _coins.postValue(repository.loadCoins(currencyFilterValue, changeTimelineFilterValue))
        }
    }
}
