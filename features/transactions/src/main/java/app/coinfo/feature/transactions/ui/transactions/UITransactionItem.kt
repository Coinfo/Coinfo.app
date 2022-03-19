package app.coinfo.feature.transactions.ui.transactions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UITransactionItem(
    val id: Long,
    val date: String,
    @StringRes val typeName: Int,
    @DrawableRes val typeImage: Int,
    val amount: String,
    val worth: String,
)
