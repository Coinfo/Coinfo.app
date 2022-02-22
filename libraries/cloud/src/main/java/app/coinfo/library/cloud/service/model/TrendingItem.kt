package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

internal data class TrendingItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("price_btc")
    val priceBtc: Double,
    @SerializedName("score")
    val score: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("large")
    val imageLarge: String,
    @SerializedName("small")
    val imageSmall: String,
    @SerializedName("thumb")
    val thumbnail: String
)
