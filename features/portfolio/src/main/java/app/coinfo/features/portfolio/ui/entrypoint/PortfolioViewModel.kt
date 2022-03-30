package app.coinfo.features.portfolio.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.features.portfolio.R
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.safeValue
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.model.Coin
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Asset
import app.coinfo.repository.portfolios.model.Portfolio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
internal class PortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository,
    private val coinsRepository: CoinsRepository,
    preferences: Preferences,
) : ViewModel() {

    val currentCurrency: LiveData<Currency>
        get() = _currentCurrency
    private val _currentCurrency = MutableLiveData(preferences.loadCurrency())

    val portfolioName: LiveData<String>
        get() = _portfolioName
    private val _portfolioName = MutableLiveData("")

    val worth: LiveData<Double>
        get() = _worth
    private val _worth = MutableLiveData(0.0)

    val symbol: LiveData<String>
        get() = _symbol
    private val _symbol = MutableLiveData("")

    val totalProfitLoss: LiveData<Double>
        get() = _totalProfitLoss
    private val _totalProfitLoss = MutableLiveData(0.0)

    val totalProfitLossPercentage: LiveData<Double>
        get() = _totalProfitLossPercentage
    private val _totalProfitLossPercentage = MutableLiveData(0.0)

    val isTotalProfitLossTrendPositive: LiveData<Boolean>
        get() = _isTotalProfitLossTrendPositive
    private val _isTotalProfitLossTrendPositive = MutableLiveData(false)

    val assets: LiveData<List<UIAssetsItem>>
        get() = _portfolios
    private val _portfolios = MutableLiveData(emptyList<UIAssetsItem>())

    fun loadAssets(portfolioId: Long) {
        viewModelScope.launch {
            val portfolio = portfoliosRepository.loadPortfolio(portfolioId, true)
            val coinsMap = coinsRepository.loadCoins(portfolio.assets.coins, _currentCurrency.safeValue).associateBy { it.id }
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
                    totalPrice = "${totalPrice.toStringWithSuffix(2)}${_currentCurrency.safeValue.symbol}",
                    totalProfitLoss = "0",
                    price = "${coin.currentPrice.toStringWithSuffix(2)}${_currentCurrency.safeValue.symbol}"
                )
            }

            createUIPortfolioItem(portfolio.assets.assets, coinsMap)
        }
    }

    private suspend fun createUIPortfolioItem(
        assets: List<Asset>,
        coins: Map<String, Coin>
    ) = withContext(Dispatchers.IO) {
        var totalSell = 0.0
        var totalBuy = 0.0
        var totalWorth = 0.0
        assets.forEach { asset ->
            var totalAmountPerAsset = 0.0
            asset.transactions.forEach { transaction ->
                when (transaction.type) {
                    TransactionType.BUY -> {
                        totalAmountPerAsset += transaction.amount
                        totalBuy += transaction.amount * transaction.pricePerCoin
                    }
                    TransactionType.SELL -> {
                        totalAmountPerAsset += transaction.amount
                        totalSell += transaction.amount * transaction.pricePerCoin
                    }
                }
            }
            totalWorth += totalAmountPerAsset * coins[asset.coinId]!!.currentPrice
        }

        val profitLoss = totalWorth - totalBuy + totalSell
        val profitLossPercentage = if (totalBuy == 0.0) 0.0 else (profitLoss / totalBuy) * HUNDRED_PERCENT
        val isTrendPositive = profitLoss >= 0
        val symbol = if (profitLoss == 0.0) "" else if (profitLoss > 0) "+ " else "- "

        withContext(Dispatchers.Main) {
            _worth.value = abs(totalWorth)
            _symbol.value = symbol
            _totalProfitLoss.value = abs(profitLoss)
            _totalProfitLossPercentage.value = abs(profitLossPercentage)
            _isTotalProfitLossTrendPositive.value = isTrendPositive
        }
    }

    companion object {
        private const val HUNDRED_PERCENT = 100
    }
}
