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

package app.coinfo.feature.search.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.search.ui.entrypoint.adapters.results.UISearchItem
import app.coinfo.feature.search.ui.entrypoint.adapters.trending.UITrendingItem
import app.coinfo.repository.coins.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val cloud: CoinsRepository,
) : ViewModel() {

    private var delayJob: Job? = null

    val searchResults: LiveData<List<UISearchItem>>
        get() = _searchResults
    private val _searchResults = MutableLiveData(emptyList<UISearchItem>())

    val trendingResults: LiveData<List<UITrendingItem>>
        get() = _trendingResults
    private val _trendingResults = MutableLiveData(emptyList<UITrendingItem>())

    val from: LiveData<String>
        get() = _from
    private val _from = MutableLiveData("")

    init {
        loadTrending()
    }

    fun setFrom(from: String) {
        _from.value = from
    }

    fun onSearchTextChanged(text: CharSequence) {
        delayJob?.cancel()
        delayJob = viewModelScope.launch {
            delay(DELAY_BEFORE_SEARCH)
            if (delayJob?.isCancelled == false) {
                val searchResult = cloud.search(text.toString())
                _searchResults.value = searchResult.coins.map {
                    UISearchItem(
                        it.id, it.symbol, it.name, it.marketCapRank.toString(), it.image
                    )
                }
            }
        }
    }

    private fun loadTrending() {
        viewModelScope.launch {
            val trendingResult = cloud.getTrending()
            _trendingResults.value = trendingResult.coins.map {
                UITrendingItem(
                    it.id, it.symbol, it.name, it.marketCapRank.toString(), it.image
                )
            }
        }
    }

    companion object {
        private const val DELAY_BEFORE_SEARCH = 500L
    }
}
