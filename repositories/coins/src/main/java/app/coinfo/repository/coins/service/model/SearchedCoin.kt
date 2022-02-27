package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class SearchedCoin(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("large")
    val large: String,
)
