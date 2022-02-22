package app.coinfo.fngindex.net.model

import com.google.gson.annotations.SerializedName

data class FearAndGreedIndexResponse(
    @SerializedName("data")
    val list: List<Data>?,
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("name")
    val name: String?
)
