/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.feature.portfolios.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.portfolios.R
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.toString
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
                createUIPortfolioItem(portfolio, portfolio.assets.assets, coinsMap)
            }
        }
    }

    private suspend fun createUIPortfolioItem(
        portfolio: Portfolio,
        assets: List<Asset>,
        coins: Map<String, Coin>
    ): UIPortfolioItem = withContext(Dispatchers.IO) {
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

        UIPortfolioItem(
            id = portfolio.id,
            name = portfolio.name,
            worth = "$symbol${currentCurrency.symbol}${(abs(totalWorth)).toString(2)}",
            totalProfitLoss = "$symbol${currentCurrency.symbol}${abs(profitLoss).toString(2)}",
            totalProfitLossPercentage = "${abs(profitLossPercentage).toString(2)}%",
            trendImageRes = if (isTrendPositive) {
                R.drawable.design_ic_positive_trend
            } else { R.drawable.design_ic_negative_trend },
            trendColor = if (isTrendPositive) R.color.trendPositive else R.color.trendNegative
        )
    }

    companion object {
        private const val HUNDRED_PERCENT = 100
    }
}
