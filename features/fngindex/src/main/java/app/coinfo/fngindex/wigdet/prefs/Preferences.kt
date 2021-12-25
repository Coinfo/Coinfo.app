package app.coinfo.fngindex.wigdet.prefs

import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex

interface Preferences {

    /** Sets the [DailyFearAndGreedIndex] to preferences. */
    suspend fun setDailyFearAndGreedIndex(data: DailyFearAndGreedIndex)

    /** Gets [DailyFearAndGreedIndex] from preferences. */
    fun getDailyFearAndGreedIndex(): DailyFearAndGreedIndex?

    /** Removes all data related to [DailyFearAndGreedIndex] from preferences. */
    fun removeDailyFearAndGreedIndex()
}
