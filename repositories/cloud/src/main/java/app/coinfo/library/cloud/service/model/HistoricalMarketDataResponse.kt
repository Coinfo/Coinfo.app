package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

internal data class HistoricalMarketDataResponse(
    @SerializedName("market_caps")
    val marketCap: List<List<Double>>,
    @SerializedName("prices")
    val prices: List<List<Double>>,
    @SerializedName("total_volumes")
    val totalVolumes: List<List<Double>>
)
