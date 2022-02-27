package app.coinfo.repository.coins.service.model

import com.google.gson.annotations.SerializedName

internal data class CoinsListItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)
