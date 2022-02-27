package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class CoinCurrentDataResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val descriptions: Map<String, String>,
    @SerializedName("market_data")
    val marketData: CoinMarketData,
    @SerializedName("developer_data")
    val developerData: DeveloperData?,
)
