package app.coinfo.feature.portfolios.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreatePortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    fun onCreatePortfolio(name: String) {
        viewModelScope.launch {
            portfoliosRepository.createPortfolio(name)
        }
    }
}
