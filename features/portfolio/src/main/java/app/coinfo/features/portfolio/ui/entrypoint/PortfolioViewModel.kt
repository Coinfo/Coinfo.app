package app.coinfo.features.portfolio.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository,
    private val coinsRepository: CoinsRepository,
) : ViewModel() {

    val assets: LiveData<List<UIAssetsItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIAssetsItem>())

    fun loadAssets(portfolioId: Long) {
        viewModelScope.launch {
            val portfolioAssets = portfoliosRepository.loadAssets(portfolioId)
            val coinsMap = coinsRepository.loadCoins(portfolioAssets.coins, Currency.EUR).associateBy { it.id }

            _portfolios.value = portfolioAssets.assets.map {
                val coin = coinsMap[it.coinId]!!
                UIAssetsItem(
                    id = it.coinId,
                    name = coin.name,
                    symbol = coin.symbol,
                    icon = coin.image,
                    totalHoldings = it.getAssetAmount().toString(),
                    totalPrice = (it.getAssetAmount() * coin.currentPrice).toString(),
                    totalProfitLoss = "0",
                    price = coin.currentPrice.toString()
                )
            }
        }
    }
}
