package app.coinfo.feature.portfolios.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditPortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    val portfolioName: LiveData<String>
        get() = _portfolioName
    private val _portfolioName = MutableLiveData("")

    fun loadPortfolioDetails(id: Long) {
        viewModelScope.launch {
            with(portfoliosRepository.loadPortfolio(id)) {
                _portfolioName.value = name
            }
        }
    }
}
