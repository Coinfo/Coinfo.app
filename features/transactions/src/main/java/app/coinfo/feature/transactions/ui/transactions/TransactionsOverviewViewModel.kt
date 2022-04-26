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

package app.coinfo.feature.transactions.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.transactions.R
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.safeValue
import app.coinfo.library.core.ktx.toFormattedDate
import app.coinfo.library.core.ktx.toString
import app.coinfo.library.preferences.Preferences
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.model.CoinData
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class TransactionsOverviewViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val portfoliosRepository: PortfoliosRepository,
    preferences: Preferences,
) : ViewModel() {

    private var buyPricePerCoin = 0.0
    private var buyAmount = 0.0
    private var buyWorth = 0.0
    private var sellPricePerCoin = 0.0
    private var sellAmount = 0.0
    private var sellWorth = 0.0

    val transactions: LiveData<List<UITransactionItem>>
        get() = _transactions
    private val _transactions = MutableLiveData(emptyList<UITransactionItem>())

    val totalAmount: LiveData<Double>
        get() = _totalAmount
    private val _totalAmount = MutableLiveData(0.0)

    val totalWorth: LiveData<Double>
        get() = _totalWorth
    private val _totalWorth = MutableLiveData(0.0)

    val allTimeProfitLoss: LiveData<Double>
        get() = _allTimeProfitLoss
    private val _allTimeProfitLoss = MutableLiveData(0.0)

    val allTimeProfitLossPercentage: LiveData<Double>
        get() = _allTimeProfitLossPercentage
    private val _allTimeProfitLossPercentage = MutableLiveData(0.0)

    val currency: LiveData<Currency>
        get() = _currency
    private val _currency = MutableLiveData(preferences.loadCurrency())

    val averageBuy: LiveData<Double>
        get() = _averageBuy
    private val _averageBuy = MutableLiveData(0.0)

    val averageSell: LiveData<Double>
        get() = _averageSell
    private val _averageSell = MutableLiveData(0.0)

    fun loadTransactions(portfolioId: Long, coinId: String) {
        cleanup()
        viewModelScope.launch {
            val coin = coinsRepository.getCoinData(coinId)
            val transactions = portfoliosRepository.loadTransactions(portfolioId, coinId)
            _transactions.value = transactions.mapIndexed { index, transaction ->

                _totalAmount.value = _totalAmount.safeValue + transaction.amount
                _totalWorth.value = _totalWorth.safeValue +
                    (transaction.amount * coin.getCurrentPrice(_currency.safeValue))

                calculateAverageBuyAndSell(transaction, coin)

                if (transactions.lastIndex == index) {
                    if (buyAmount > 0) {
                        _averageBuy.value = buyPricePerCoin / buyAmount
                    }
                    if (sellAmount > 0) {
                        _averageSell.value = abs(sellPricePerCoin / sellAmount)
                    }

                    _allTimeProfitLoss.value = _totalWorth.safeValue - buyWorth + sellWorth
                    _allTimeProfitLossPercentage.value = (_allTimeProfitLoss.safeValue / buyWorth) * HUNDRED_PERCENT
                }

                UITransactionItem(
                    id = transaction.id,
                    date = transaction.date.toFormattedDate(),
                    typeName = transaction.type.resId,
                    typeImage = transaction.typeAsImage,
                    amount = transaction.formattedAmount,
                    worth = transaction.formattedWorth
                )
            }
        }
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            portfoliosRepository.deleteTransaction(id)
        }
    }

    private val Transaction.formattedWorth
        get() = "${currency.symbol}${(amount * pricePerCoin).toString(2)}"

    private val Transaction.formattedAmount
        get() = "$amount ${symbol.uppercase()}"

    private val Transaction.typeAsImage
        get() = when (type) {
            TransactionType.SELL -> R.drawable.transactions_ic_sell
            TransactionType.BUY -> R.drawable.transactions_ic_buy
        }

    private fun calculateAverageBuyAndSell(transaction: Transaction, coinData: CoinData) {
        when (transaction.type) {
            TransactionType.BUY -> {
                buyAmount += transaction.amount
                buyPricePerCoin += if (transaction.currency == _currency.safeValue) {
                    buyWorth += (transaction.amount * transaction.pricePerCoin)
                    transaction.pricePerCoin
                } else {
                    val pricePerCoin = calculatePricePerCoin(transaction, coinData)
                    buyWorth += (transaction.amount * pricePerCoin)
                    pricePerCoin
                }
            }
            TransactionType.SELL -> {
                sellAmount += abs(transaction.amount)
                sellPricePerCoin += if (transaction.currency == _currency.safeValue) {
                    sellWorth += (transaction.amount * transaction.pricePerCoin)
                    transaction.pricePerCoin
                } else {
                    val pricePerCoin = calculatePricePerCoin(transaction, coinData)
                    sellWorth += (transaction.amount * pricePerCoin)
                    pricePerCoin
                }
            }
        }
    }

    private fun cleanup() {
        buyPricePerCoin = 0.0
        buyAmount = 0.0
        buyWorth = 0.0
        sellPricePerCoin = 0.0
        sellAmount = 0.0
        sellWorth = 0.0
        _totalAmount.value = 0.0
        _totalWorth.value = 0.0
        _allTimeProfitLoss.value = 0.0
        _allTimeProfitLossPercentage.value = 0.0
        _averageBuy.value = 0.0
        _averageSell.value = 0.0
    }

    private fun calculatePricePerCoin(transaction: Transaction, coinData: CoinData): Double {
        val priceNativeCurrency = coinData.getCurrentPrice(transaction.currency)
        val priceExpectCurrency = coinData.getCurrentPrice(_currency.safeValue)
        return (transaction.pricePerCoin * (priceExpectCurrency / priceNativeCurrency))
    }

    companion object {
        private const val HUNDRED_PERCENT = 100
    }
}
