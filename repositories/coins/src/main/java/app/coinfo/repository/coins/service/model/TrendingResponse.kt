package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class TrendingResponse(
    @SerializedName("coins")
    val coins: List<TrendingCoin>,
)
