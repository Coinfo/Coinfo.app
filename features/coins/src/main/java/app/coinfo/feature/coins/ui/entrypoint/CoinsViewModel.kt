package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.CoinListItem
import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.library.core.utils.Currency
import app.coinfo.library.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    private val currentCurrency: Currency
        get() = Currency.valueOf(preferences.loadCurrency())

    private val nextCurrency: Currency
        get() {
            val listOfSupportedCurrencies = listOf(Currency.EUR, Currency.USD)
            val indexOfCurrentCurrency = listOfSupportedCurrencies.indexOf(currentCurrency)
            return if (indexOfCurrentCurrency == listOfSupportedCurrencies.size - 1) {
                listOfSupportedCurrencies[0]
            } else {
                listOfSupportedCurrencies[indexOfCurrentCurrency + 1]
            }
        }

    val changeTimeline: LiveData<String>
        get() = _changeTimeline
    private val _changeTimeline = MutableLiveData(
        preferences.loadChangeTimeline()
            ?: ChangeTimelineFilterItem.DAY.toString()
    )

    val currency: LiveData<String>
        get() = _currency
    private val _currency = MutableLiveData(preferences.loadCurrency())

    val coins: LiveData<List<CoinListItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<CoinListItem>())

    init {
        loadCoins(currentCurrency, ChangeTimelineFilterItem.YEAR)
    }

    private fun loadCoins(currency: Currency, changeTimeline: ChangeTimelineFilterItem) {
        viewModelScope.launch {
            _coins.postValue(repository.loadCoins(currency, changeTimeline))
        }
    }

    fun setChangeTimeline(value: ChangeTimelineFilterItem) {
        preferences.saveChangeTimeline(value.toString())
        _changeTimeline.value = value.toString()
        loadCoins(currentCurrency, value)
    }

    fun loadNextCurrency() = with(nextCurrency) {
        preferences.saveCurrency(this.name)
        _currency.value = this.name
        loadCoins(this, ChangeTimelineFilterItem.YEAR)
    }
}
