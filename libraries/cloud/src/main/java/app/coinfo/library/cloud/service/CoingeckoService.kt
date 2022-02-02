package app.coinfo.library.cloud.service

import app.coinfo.library.cloud.service.model.CoinsList
import app.coinfo.library.cloud.service.model.MarketsList
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
        @Query("vs_currencies") currencies: String,
    ): Map<String, Map<String, Double>>

    @GET("coins/{id}")
    suspend fun coinInfo(@Path("id") id: String)

    /**
     *  List all supported coins price, market cap, volume, and market related data.
     *
     *  @param targetCurrency - The target currency of market data (usd, eur, jpy, etc.)
     *  @param order - sort results by field. Valid values: market_cap_desc, gecko_desc, gecko_asc,
     *  market_cap_asc, market_cap_desc, volume_asc, volume_desc, id_asc, id_desc
     *  @param perPage - Total results per page, valid values: 1..250
     *  @param page - Page through results
     */
    @GET("coins/markets")
    suspend fun coinMarkets(
        @Query("vs_currency") targetCurrency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("price_change_percentage") priceChangePercentage: String,
    ): MarketsList

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }
}
