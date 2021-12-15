package app.coinfo.fngindex.net

import app.coinfo.fngindex.net.model.FearAndGreedIndexResponse
import retrofit2.http.GET

/**
 * Service provides access to the Fear and greed Index
 */
internal interface AlternativeService {

    @GET("fng/")
    suspend fun requestLatestFearAndGreedIndex(): FearAndGreedIndexResponse
}
