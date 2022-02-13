package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

data class CoinCurrentDataResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("market_data")
    val marketData: CoinMarketData,
    @SerializedName("developer_data")
    val developerData: DeveloperData?,
)
