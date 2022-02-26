package app.coinfo.feature.portfolios.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfoliosViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    val portfolios: LiveData<List<UIPortfolioItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIPortfolioItem>())

    fun loadPortfolios() {
        viewModelScope.launch {
            _portfolios.value = portfoliosRepository.loadPortfolios().map {
                UIPortfolioItem(id = it.id, name = it.name)
            }
        }
    }
}
