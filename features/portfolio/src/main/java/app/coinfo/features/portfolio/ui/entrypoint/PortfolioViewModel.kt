package app.coinfo.features.portfolio.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository,
    private val coinsRepository: CoinsRepository,
    private val preferences: Preferences,
) : ViewModel() {

    private val currentCurrency: Currency
        get() = preferences.loadCurrency()

    val portfolioName: LiveData<String>
        get() = _portfolioName
    private val _portfolioName = MutableLiveData<String>()

    val assets: LiveData<List<UIAssetsItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIAssetsItem>())

    fun loadAssets(portfolioId: Long) {
        viewModelScope.launch {
            val portfolio = portfoliosRepository.loadPortfolio(portfolioId, true)
            val coinsMap = coinsRepository.loadCoins(portfolio.assets.coins, currentCurrency).associateBy { it.id }
            _portfolioName.value = portfolio.name
            _portfolios.value = portfolio.assets.assets.map {
                val coin = coinsMap[it.coinId]!!
                val totalPrice = it.getAssetAmount() * coin.currentPrice
                UIAssetsItem(
                    id = it.coinId,
                    name = coin.name,
                    symbol = coin.symbol,
                    icon = coin.image,
                    totalHoldings = it.getAssetAmount().toString(),
                    totalPrice = "${totalPrice.toStringWithSuffix(2)}${currentCurrency.symbol}",
                    totalProfitLoss = "0",
                    price = "${coin.currentPrice.toStringWithSuffix(2)}${currentCurrency.symbol}"
                )
            }
        }
    }
}
