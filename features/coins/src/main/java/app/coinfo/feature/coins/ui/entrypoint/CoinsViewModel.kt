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

package app.coinfo.feature.coins.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.coins.model.UICoinItem
import app.coinfo.feature.coins.prefs.CoinsPreferences
import app.coinfo.feature.coins.repos.CoinsInternalRepository
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinsViewModel @Inject constructor(
    private val repository: CoinsInternalRepository,
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

    val coins: LiveData<List<UICoinItem>>
        get() = _coins
    private val _coins = MutableLiveData(emptyList<UICoinItem>())

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
        loadCoins()
    }

    private fun loadCoins() = viewModelScope.launch {
        val ids = if (localPreferences.isFavoritesModeActive()) { preferences.loadFavoriteCoins() } else emptyList()
        _timeInterval.value = currentTimeInterval
        _currency.value = currentCurrency
        _coins.value = repository.loadCoins(ids, currentCurrency, currentTimeInterval)
    }
}
