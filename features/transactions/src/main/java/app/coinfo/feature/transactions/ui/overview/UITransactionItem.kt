package app.coinfo.feature.transactions.ui.overview

import androidx.annotation.DrawableRes

data class UITransactionItem(
    val id: Long,
    val date: String,
    val typeName: String,
    @DrawableRes val typeImage: Int,
    val amount: String,
    val worth: String,
)
