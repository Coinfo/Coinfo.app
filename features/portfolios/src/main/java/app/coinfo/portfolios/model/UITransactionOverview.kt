package app.coinfo.portfolios.model

import app.coinfo.library.database.model.TransactionType

data class UITransactionOverview(
    val assetID: String,
) {
    private var _totalHoldings: Double = 0.0
    /** Returns sum holdings (Buy - Sell + Reward). */
    val totalHoldings: Double
        get() = _totalHoldings

    private var _totalBoughtAmount = 0.0
    val totalBoughtAmount: Double
        get() = _totalBoughtAmount

    val boughtAverage: Double
        get() = if (_totalBoughtAmount == 0.0) 0.0 else _totalBoughtInCash / _totalBoughtAmount

    private var _totalSoldAmount = 0.0
    val totalSoldAmount: Double
        get() = _totalSoldAmount

    val soldAverage: Double
        get() = if (_totalSoldAmount == 0.0) 0.0 else _totalSoldInCash / _totalSoldAmount

    private var _totalRewardAmount = 0.0
    val totalRewardAmount: Double
        get() = _totalRewardAmount

    private var _totalRewardInCash = 0.0
    val totalRewardInCash: Double
        get() = _totalRewardInCash

    private var _totalBuyCount = 0.0

    private var _totalSellCount = 0.0

    private var _totalBoughtInCash = 0.0

    private var _totalSoldInCash = 0.0

    private var _currentPrice: Double = 0.0

    val totalWorth: Double
        get() = totalHoldings * _currentPrice

    private var _currency: String = ""
    val currency: String
        get() = _currency

    fun calculateOverview(type: TransactionType, currency: String, amount: Double, price: Double) {
        _currency = currency
        when (type) {
            TransactionType.BUY -> {
                _totalBoughtInCash += price
                _totalBoughtAmount += amount
                _totalBuyCount++
            }
            TransactionType.SELL -> {
                _totalSoldInCash += price
                _totalSoldAmount += amount
                _totalSellCount++
            }
            TransactionType.STAKE_REWARD, TransactionType.INTEREST_PAID, TransactionType.INTEREST_EARN -> {
                _totalRewardAmount += amount
                _totalRewardInCash += price
            }
            else -> Unit
        }
        _totalHoldings += amount
    }

    fun currentPrice(currentPrice: Double) {
        _currentPrice = currentPrice
    }
}
