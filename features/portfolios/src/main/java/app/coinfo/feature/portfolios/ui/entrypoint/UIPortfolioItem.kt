package app.coinfo.feature.portfolios.ui.entrypoint

import androidx.annotation.DrawableRes

data class UIPortfolioItem(
    val id: Long,
    val name: String,
    val worth: String,
    val totalProfitLoss: String,
    val totalProfitLossPercentage: String,
    @DrawableRes val trendImageRes: Int,
    val trendColor: Int
)
