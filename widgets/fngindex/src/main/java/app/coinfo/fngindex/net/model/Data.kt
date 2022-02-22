package app.coinfo.fngindex.net.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("time_until_update")
    val timeUntilUpdate: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("value_classification")
    val valueClassification: String
)
