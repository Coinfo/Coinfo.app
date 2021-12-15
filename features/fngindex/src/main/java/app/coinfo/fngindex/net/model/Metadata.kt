package app.coinfo.fngindex.net.model

import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("error")
    val error: String?
)
