package app.coinfo.fngindex.wigdet.prefs

import app.coinfo.fngindex.wigdet.model.DailyFearAndGreedIndex

interface Preferences {

    fun setDailyFearAndGreedIndex(data: DailyFearAndGreedIndex)

    fun getDailyFearAndGreedIndex(): DailyFearAndGreedIndex?

    fun removeDailyFearAndGreedIndex()
}
