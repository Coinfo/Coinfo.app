package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class MarketsListItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("current_price")
    val currentPrice: Double,

    @SerializedName("market_cap")
    val marketCap: Double,

    @SerializedName("market_cap_rank")
    val marketCapRank: Int,

    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1h: Double?,

    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24h: Double?,

    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7d: Double?,

    @SerializedName("price_change_percentage_14d_in_currency")
    val priceChangePercentage14d: Double?,

    @SerializedName("price_change_percentage_30d_in_currency")
    val priceChangePercentage30d: Double?,

    @SerializedName("price_change_percentage_200d_in_currency")
    val priceChangePercentage200d: Double?,

    @SerializedName("price_change_percentage_1y_in_currency")
    val priceChangePercentage1y: Double?,
)
