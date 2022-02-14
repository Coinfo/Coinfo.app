package app.coinfo.feature.coin.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.cloud.Cloud
import app.coinfo.library.cloud.enums.Currency
import app.coinfo.library.cloud.enums.TimeInterval
import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.model.DeveloperInfo
import app.coinfo.library.cloud.model.PriceDatePair
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinViewModel @Inject constructor(
    private val cloud: Cloud
) : ViewModel() {

    private lateinit var id: String
    private lateinit var coin: CoinData

    val name: LiveData<String>
        get() = _name
    private val _name = MutableLiveData("")

    val developerInfo: LiveData<DeveloperInfo?>
        get() = _developerInfo
    private val _developerInfo = MutableLiveData<DeveloperInfo?>(null)

    val price: LiveData<String>
        get() = _price
    private val _price = MutableLiveData("")

    val marketCap: LiveData<String>
        get() = _marketCap
    private val _marketCap = MutableLiveData("")

    val maxSupply: LiveData<String>
        get() = _maxSupply
    private val _maxSupply = MutableLiveData("")

    val allTimeHigh: LiveData<String>
        get() = _allTimeHigh
    private val _allTimeHigh = MutableLiveData("")

    val allTimeLow: LiveData<String>
        get() = _allTimeLow
    private val _allTimeLow = MutableLiveData("")

    val percentage: LiveData<String>
        get() = _percentage
    private val _percentage = MutableLiveData("")

    val priceHistoricalDataSet: LiveData<List<PriceDatePair>>
        get() = _priceHistoricalDataSet
    private val _priceHistoricalDataSet = MutableLiveData(emptyList<PriceDatePair>())

    fun loadCoinData(id: String) {
        this.id = id
        loadCoinInformation(TimeInterval.DAY)
        loadCoinHistoricalMarketData(TimeInterval.DAY)
    }

    fun onTimeIntervalChanged(interval: TimeInterval) {
        // Change Price Percentage Change.
        _percentage.value = coin.getPercentageChange(Currency.EUR, interval).toString(2)
        // Reload Historical Market Data.
        loadCoinHistoricalMarketData(interval)
    }

    private fun loadCoinInformation(interval: TimeInterval) {
        viewModelScope.launch {
            coin = cloud.getCoinData(id)

            _name.value = coin.name
            _price.value = coin.getCurrentPrice(Currency.EUR).toString(2)
            _percentage.value = coin.getPercentageChange(Currency.EUR, interval).toString(2)
            _developerInfo.value = coin.developerInfo
            _marketCap.value = coin.getMarketCap(Currency.EUR).toStringWithSuffix(2)
            _maxSupply.value = coin.maxSupply.toStringWithSuffix(2)
            _allTimeHigh.value = coin.getAllTimeHigh(Currency.EUR).toStringWithSuffix(2)
            _allTimeLow.value = coin.getAllTimeLow(Currency.EUR).toStringWithSuffix(2)
        }
    }

    private fun loadCoinHistoricalMarketData(interval: TimeInterval) {
        viewModelScope.launch {
            val historicalData = cloud.getCoinHistoricalMarketData(id, Currency.EUR, interval)
            _priceHistoricalDataSet.value = historicalData.prices
        }
    }
}
