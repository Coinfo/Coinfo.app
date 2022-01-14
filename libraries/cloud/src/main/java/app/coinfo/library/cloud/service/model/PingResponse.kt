package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

data class PingResponse(

    @SerializedName("gecko_says")
    val status: String
)
