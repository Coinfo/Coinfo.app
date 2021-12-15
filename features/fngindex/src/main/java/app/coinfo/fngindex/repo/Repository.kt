package app.coinfo.fngindex.repo

import app.coinfo.fngindex.repo.model.FearAndGreedIndex

internal interface Repository {

    suspend fun getDailyFearAndGreedIndex(): FearAndGreedIndex
}
