package app.coinfo.library.cloud.service

import app.coinfo.library.cloud.service.model.PingResponse
import retrofit2.http.GET

interface CoingeckoService {

    @GET("ping")
    suspend fun ping(): PingResponse

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }
}
