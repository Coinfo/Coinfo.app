package app.coinfo.fngindex.repo

import app.coinfo.fngindex.model.FearAndGreedIndex
import app.coinfo.fngindex.util.ResultWrapper

internal interface Repository {

    suspend fun getDailyFearAndGreedIndex(): ResultWrapper<FearAndGreedIndex>
}
