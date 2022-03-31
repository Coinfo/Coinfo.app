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
import app.coinfo.repository.portfolios.PortfoliosRepository
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
            val coins = coinsRepository.loadCoins(
                portfolio.assets.coins, _currentCurrency.safeValue
            ).associateBy { it.id }

            withContext(Dispatchers.IO) {
                var totalSell = 0.0
                var totalBuy = 0.0
                var totalWorth = 0.0
                val listOfAssets = mutableListOf<UIAssetsItem>()

                portfolio.assets.assets.forEach { asset ->
                    var totalAmountPerAsset = 0.0
                    var totalSellForAsset = 0.0
                    var totalBuyForAsset = 0.0
                    var totalWorthForAsset = 0.0

                    asset.transactions.forEach { transaction ->
                        when (transaction.type) {
                            TransactionType.BUY -> {
                                totalAmountPerAsset += transaction.amount
                                totalBuyForAsset += transaction.amount * transaction.pricePerCoin
                            }
                            TransactionType.SELL -> {
                                totalAmountPerAsset += transaction.amount
                                totalSellForAsset += transaction.amount * transaction.pricePerCoin
                            }
                        }
                    }
                    totalWorthForAsset += totalAmountPerAsset * coins[asset.coinId]!!.currentPrice

                    val coin = coins[asset.coinId]!!
                    val profitLossForAsset = totalWorthForAsset - totalBuyForAsset + totalSellForAsset
                    val profitLossPercentageForAsset = if (totalBuyForAsset == 0.0)
                        0.0 else (profitLossForAsset / totalBuyForAsset) * HUNDRED_PERCENT
                    val isTrendPositiveForAsset = profitLossForAsset >= 0

                    listOfAssets.add(
                        UIAssetsItem(
                            id = asset.coinId,
                            name = coin.name,
                            symbol = coin.symbol,
                            icon = coin.image,
                            totalHoldings = totalAmountPerAsset.toString(),
                            totalPrice = totalWorthForAsset.toStringWithSuffix(2) +
                                _currentCurrency.safeValue.symbol,
                            totalProfitLoss = profitLossForAsset.toString(2),
                            totalProfitLossPercentage = "${profitLossPercentageForAsset.toString(2)}%",
                            price = coin.currentPrice.toStringWithSuffix(2) + _currentCurrency.safeValue.symbol,
                            color = if (isTrendPositiveForAsset) R.color.trendPositive else R.color.trendNegative,
                            trend = if (isTrendPositiveForAsset) R.drawable.design_ic_positive_trend
                            else R.drawable.design_ic_negative_trend
                        )
                    )

                    totalSell += totalSellForAsset
                    totalBuy += totalBuyForAsset
                    totalWorth += totalWorthForAsset
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
                    _portfolios.value = listOfAssets
                }
            }
        }
    }

    companion object {
        private const val HUNDRED_PERCENT = 100
    }
}
