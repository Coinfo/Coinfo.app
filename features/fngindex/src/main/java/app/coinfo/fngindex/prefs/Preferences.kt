package app.coinfo.fngindex.prefs

import app.coinfo.fngindex.model.FearAndGreedIndex

interface Preferences {

    fun setFearAndGreedIndex(index: FearAndGreedIndex)

    fun getFearAndGreedIndex(): FearAndGreedIndex

    fun getLastUpdateDate(): Long

    fun getNextUpdateDate(): Long

    fun getFearAndGreedIndexValue(): String

    fun getFearAndGreedIndexValueName(): String

    fun hasSavedFearAndGreedIndex(): Boolean

    fun reset()
}
