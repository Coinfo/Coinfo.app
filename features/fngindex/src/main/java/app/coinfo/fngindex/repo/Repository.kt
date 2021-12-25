package app.coinfo.fngindex.repo

import app.coinfo.fngindex.util.ResultWrapper
import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex

internal interface Repository {

    suspend fun getDailyFearAndGreedIndexForWidget(): ResultWrapper<DailyFearAndGreedIndex>
}
