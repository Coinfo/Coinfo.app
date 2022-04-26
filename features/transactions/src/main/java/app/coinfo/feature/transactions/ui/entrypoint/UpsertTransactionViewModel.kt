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

package app.coinfo.feature.transactions.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.core.ktx.safeValue
import app.coinfo.library.core.ktx.toDoubleOrZero
import app.coinfo.library.core.ktx.toStringWithSuffix
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class UpsertTransactionViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val portfoliosRepository: PortfoliosRepository,
) : ViewModel() {

    private var coinId: String? = null
    private var portfolioId: Long? = null
    private var transactionId: Long = 0L

    val transactionType: LiveData<TransactionType>
        get() = _transactionType
    private val _transactionType = MutableLiveData(TransactionType.BUY)

    val amount: LiveData<Double>
        get() = _amount
    private val _amount = MutableLiveData(0.00000000000000000000)

    val price: LiveData<String>
        get() = _price
    private val _price = MutableLiveData("")

    val isPriceManuallyChanged: LiveData<Boolean>
        get() = _isPriceManuallyChanged
    private val _isPriceManuallyChanged = MutableLiveData(false)

    var fee: String = ""
        private set

    val isFeeManuallyChanged: LiveData<Boolean>
        get() = _isFeeManuallyChanged
    private val _isFeeManuallyChanged = MutableLiveData(false)

    var notes: String = ""
        private set

    val currency: LiveData<Currency>
        get() = _currency
    private val _currency = MutableLiveData(Currency.EUR)

    val isCurrencyManuallyChanged: LiveData<Boolean>
        get() = _isCurrencyManuallyChanged
    private val _isCurrencyManuallyChanged = MutableLiveData(false)

    val isNotesManuallyChanged: LiveData<Boolean>
        get() = _isNotesManuallyChanged
    private val _isNotesManuallyChanged = MutableLiveData(false)

    val symbol: LiveData<String>
        get() = _symbol
    private val _symbol = MutableLiveData("")

    fun loadCoinInformation(coinId: String, portfolioId: Long) {
        this.coinId = coinId
        this.portfolioId = portfolioId
        this.transactionId = 0L
        viewModelScope.launch {
            val data = coinsRepository.getCoinData(coinId)
            _price.value = data.getCurrentPrice(Currency.EUR).toStringWithSuffix(2)
            _symbol.value = data.symbol
        }
    }

    fun loadCoinInformation(transactionId: Long) {
        this.transactionId = transactionId
        viewModelScope.launch {
            val data = portfoliosRepository.loadTransaction(transactionId)
            _price.value = abs(data.pricePerCoin).toString()
            coinId = data.coinId
            portfolioId = data.portfolioId
            _symbol.value = data.symbol
            _currency.value = data.currency
            _amount.value = abs(data.amount)
            _transactionType.value = data.type
            fee = abs(data.fee).toString()
            notes = data.notes
        }
    }

    fun onUpdatePrice(price: Double) {
        _price.value = price.toString()
        _isPriceManuallyChanged.value = true
    }

    fun onAmountChanged(s: CharSequence) {
        _amount.value = s.toString().toDoubleOrZero()
    }

    fun onUpdateFee(value: Double) {
        fee = value.toString()
        _isFeeManuallyChanged.value = true
    }

    fun onUpdateNotes(value: String) {
        notes = value
        _isNotesManuallyChanged.value = true
    }

    fun onUpdateCurrency(value: Currency) {
        _currency.value = value
        _isCurrencyManuallyChanged.value = true
    }

    fun onTransactionTypeSelected(type: TransactionType) {
        _transactionType.value = type
    }

    fun onAddEditTransaction(edit: Boolean = false) {
        val transaction = Transaction(
            id = transactionId,
            coinId = coinId!!,
            portfolioId = portfolioId!!,
            symbol = _symbol.value!!,
            amount = getValueDependingOnTransactionType(_amount.safeValue),
            pricePerCoin = getValueDependingOnTransactionType(_price.value?.toDoubleOrZero() ?: 0.0),
            fee = fee.toDoubleOrZero(),
            currency = _currency.value!!,
            type = _transactionType.value!!,
            date = System.currentTimeMillis(),
            notes = notes,
        )

        viewModelScope.launch {
            if (edit) {
                portfoliosRepository.updateTransaction(transaction)
            } else {
                portfoliosRepository.addTransaction(transaction)
            }
        }
    }

    private fun getValueDependingOnTransactionType(amount: Double) = when (_transactionType.value!!) {
        TransactionType.BUY -> amount * 1.00000000000000000000
        TransactionType.SELL -> amount * -1.00000000000000000000
    }
}
