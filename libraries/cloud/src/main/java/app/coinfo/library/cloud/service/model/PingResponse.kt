package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

internal data class PingResponse(

    @SerializedName("gecko_says")
    val status: String
)
