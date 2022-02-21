package app.coinfo.feature.search.ui.entrypoint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onSearchTextChanged(text: CharSequence) {
        delayJob?.cancel()
        delayJob = viewModelScope.launch {
            delay(DELAY_BEFORE_SEARCH)
            if (delayJob?.isCancelled == false) {
                cloud.search(text.toString())
            }
        }
    }

    companion object {
        private const val DELAY_BEFORE_SEARCH = 1000L
    }
}
