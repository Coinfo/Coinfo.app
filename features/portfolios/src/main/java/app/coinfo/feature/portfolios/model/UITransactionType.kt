package app.coinfo.feature.portfolios.model

import androidx.annotation.StringRes
import app.coinfo.feature.portfolios.R

enum class UITransactionType(@StringRes val stringRes: Int) {
    BUY(R.string.ui_transaction_type_buy),
    SELL(R.string.ui_transaction_type_sell),
    INTEREST_EARN(R.string.ui_transaction_type_interest_earn),
    STAKE_REWARD(R.string.ui_transaction_type_stake_reward)
}
