package app.coinfo.feature.search.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.search.ui.entrypoint.adapters.results.UISearchResult
import app.coinfo.library.cloud.Cloud
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val cloud: Cloud,
) : ViewModel() {

    private var delayJob: Job? = null

    val searchResults: LiveData<List<UISearchResult>>
        get() = _searchResults
    private val _searchResults = MutableLiveData(emptyList<UISearchResult>())

    fun onSearchTextChanged(text: CharSequence) {
        delayJob?.cancel()
        delayJob = viewModelScope.launch {
            delay(DELAY_BEFORE_SEARCH)
            if (delayJob?.isCancelled == false) {
                val searchResult = cloud.search(text.toString())
                _searchResults.value = searchResult.coins.map {
                    UISearchResult(
                        it.id, it.symbol, it.name, it.marketCapRank.toString(), it.image
                    )
                }
            }
        }
    }

    companion object {
        private const val DELAY_BEFORE_SEARCH = 500L
    }
}
