package app.coinfo.feature.portfolios.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfoliosViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository,
    private val coinsRepository: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    private val currentCurrency: Currency
        get() = preferences.loadCurrency()

    val portfolios: LiveData<List<UIPortfolioItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIPortfolioItem>())

    fun loadPortfolios() {
        viewModelScope.launch {
            _portfolios.value = portfoliosRepository.loadPortfolios().map { portfolio ->
                val coinsMap = coinsRepository.loadCoins(portfolio.assets.coins, currentCurrency).associateBy { it.id }
                var totalValue = 0.0
                val change24h = 0.0
                val totalProfitLoss = 0.0
                portfolio.assets.assets.forEach {
                    val coin = coinsMap[it.coinId]!!
                    val coinTotalValue = it.getAssetAmount() * coin.currentPrice
                    totalValue += coinTotalValue
                }

                UIPortfolioItem(
                    id = portfolio.id,
                    name = portfolio.name,
                    totalValue = "${totalValue.toString(2)}${currentCurrency.symbol}",
                    change24Hour = "${change24h.toString(2)}${currentCurrency.symbol}",
                    totalProfitLoss = "${totalProfitLoss.toString(2)}${currentCurrency.symbol}",
                )
            }
        }
    }
}
