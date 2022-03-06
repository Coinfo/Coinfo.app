package app.coinfo.features.portfolio.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    val assets: LiveData<List<UIAssetsItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIAssetsItem>())

    fun loadAssets(portfolioId: Long) {
        viewModelScope.launch {
            _portfolios.value = portfoliosRepository.loadTransactions(portfolioId).map {
                UIAssetsItem(id = it.coinId)
            }
        }
    }
}
