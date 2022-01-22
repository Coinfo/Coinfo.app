package app.coinfo.library.cloud.service

import app.coinfo.library.cloud.service.model.CoinsList
import app.coinfo.library.cloud.service.model.PingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoingeckoService {

    @GET("ping")
    suspend fun ping(): PingResponse

    /** List all supported coins id, name and symbol */
    @GET("coins/list")
    suspend fun coinsList(): CoinsList

    /** Get the current price of any cryptocurrencies in any other supported currencies that you need. */
    @GET("simple/price")
    suspend fun coinPrice(
        @Query("ids") ids: String?,
        @Query("vs_currencies") currencies: String
    ): Map<String, Map<String, Double>>

    @GET("coins/{id}")
    suspend fun coinInfo(@Path("id") id: String)

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }
}
