package app.coinfo.feature.coin.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.PriceDatePair
import app.coinfo.library.core.ktx.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinViewModel @Inject constructor(
    private val cloud: Cloud
) : ViewModel() {

    private lateinit var coin: CoinData

    val name: LiveData<String>
        get() = _name
    private val _name = MutableLiveData("")

    val price: LiveData<String>
        get() = _price
    private val _price = MutableLiveData("")

    val percentage: LiveData<String>
        get() = _percentage
    private val _percentage = MutableLiveData("")

    val priceHistoricalDataSet: LiveData<List<PriceDatePair>>
        get() = _priceHistoricalDataSet
    private val _priceHistoricalDataSet = MutableLiveData(emptyList<PriceDatePair>())

    fun loadCoinData(id: String) {
        loadCoinInformation(id)
        loadCoinHistoricalMarketData(id)
    }

    fun onTimeIntervalChanged(interval: TimeInterval) {
        _percentage.value = coin.getPercentageChange(Currency.EUR, interval).toString(2)
    }

    private fun loadCoinInformation(id: String) {
        viewModelScope.launch {
            coin = cloud.getCoinData(id)

            _name.value = coin.name
            _price.value = coin.getCurrentPrice(Currency.EUR).toString(2)
            _percentage.value = coin.getPercentageChange(Currency.EUR, TimeInterval.DAY).toString(2)
        }
    }

    private fun loadCoinHistoricalMarketData(id: String) {
        viewModelScope.launch {
            val historicalData = cloud.getCoinHistoricalMarketData(id, Currency.EUR, TimeInterval.DAY)
            _priceHistoricalDataSet.value = historicalData.prices
        }
    }
}
