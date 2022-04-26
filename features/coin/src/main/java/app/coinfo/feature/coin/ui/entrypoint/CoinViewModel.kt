/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.feature.coin.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.coins.model.DeveloperInfo
import app.coinfo.repository.coins.model.PriceDatePair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinViewModel @Inject constructor(
    private val cloud: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    lateinit var id: String
    private lateinit var coin: CoinData

    private val currentTimeInterval: TimeInterval
        get() = preferences.loadTimeInterval()

    private val currentCurrency: Currency
        get() = preferences.loadCurrency()

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

    val fullyDilutedValuation: LiveData<String>
        get() = _fullyDilutedValuation
    private val _fullyDilutedValuation = MutableLiveData("")

    val circulatingSupply: LiveData<String>
        get() = _circulatingSupply
    private val _circulatingSupply = MutableLiveData("")

    val totalSupply: LiveData<String>
        get() = _totalSupply
    private val _totalSupply = MutableLiveData("")

    val rank: LiveData<String>
        get() = _rank
    private val _rank = MutableLiveData("")

    val percentage: LiveData<String>
        get() = _percentage
    private val _percentage = MutableLiveData("")

    val description: LiveData<String>
        get() = _description
    private val _description = MutableLiveData("")

    val priceHistoricalDataSet: LiveData<List<PriceDatePair>>
        get() = _priceHistoricalDataSet
    private val _priceHistoricalDataSet = MutableLiveData(emptyList<PriceDatePair>())

    val isRefreshingCoinActive: LiveData<Boolean>
        get() = _isRefreshingCoinActive
    private val _isRefreshingCoinActive = MutableLiveData(false)

    fun loadCoinData(id: String) {
        this.id = id
        loadCoinInformation(currentTimeInterval)
        loadCoinHistoricalMarketData(currentTimeInterval)
    }

    fun onTimeIntervalChanged(interval: TimeInterval) {
        preferences.saveTimeInterval(interval)
        // Change Price Percentage Change.
        _percentage.value = coin.getPercentageChange(currentCurrency, interval).toString(2)
        // Reload Historical Market Data.
        loadCoinHistoricalMarketData(interval)
    }

    fun onRefreshCoinData() {
        viewModelScope.launch {
            _isRefreshingCoinActive.value = true
            val timeInterval = preferences.loadTimeInterval()
            val job1 = loadCoinInformation(timeInterval)
            val job2 = loadCoinHistoricalMarketData(timeInterval)
            job1.join()
            job2.join()
            _isRefreshingCoinActive.value = false
        }
    }

    private fun loadCoinInformation(interval: TimeInterval) = viewModelScope.launch {
        coin = cloud.getCoinData(id)
        _name.value = coin.name
        _price.value = coin.getCurrentPrice(currentCurrency).toString(2)
        _percentage.value = coin.getPercentageChange(currentCurrency, interval).toString(2)
        _developerInfo.value = coin.developerInfo
        _marketCap.value = coin.getMarketCap(currentCurrency).toStringWithSuffix(2)
        _maxSupply.value = coin.maxSupply.toStringWithSuffix(2)
        _allTimeHigh.value = coin.getAllTimeHigh(currentCurrency).toStringWithSuffix(2)
        _allTimeLow.value = coin.getAllTimeLow(currentCurrency).toStringWithSuffix(2)
        _circulatingSupply.value = coin.circulatingSupply.toStringWithSuffix(2)
        _totalSupply.value = coin.totalSupply.toStringWithSuffix(2)
        _rank.value = coin.rank.toString()
        _fullyDilutedValuation.value = coin.getFullyDilutedValuation(currentCurrency).toStringWithSuffix(2)
        _description.value = coin.description
    }

    private fun loadCoinHistoricalMarketData(interval: TimeInterval) = viewModelScope.launch {
        val historicalData = cloud.getCoinHistoricalMarketData(id, currentCurrency, interval)
        _priceHistoricalDataSet.value = historicalData.prices
    }
}
