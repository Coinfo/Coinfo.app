package app.coinfo.fngindex.repo

import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.repo.model.FearAndGreedIndex

internal class FearAndGreedIndexRepository(
    private val service: AlternativeService,
) : Repository {

    override suspend fun getDailyFearAndGreedIndex(): FearAndGreedIndex {
        val result = service.requestLatestFearAndGreedIndex()


        return FearAndGreedIndex(result.name, result.list[0].value)
    }

    companion object{
        private const val TAG = "FNG/Repository"
    }
}
