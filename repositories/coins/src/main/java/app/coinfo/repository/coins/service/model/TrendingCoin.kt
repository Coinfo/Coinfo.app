package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class TrendingCoin(
    @SerializedName("item")
    val item: TrendingItem
)
