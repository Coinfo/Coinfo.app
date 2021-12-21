package app.coinfo.fngindex.model

sealed class FearAndGreedIndex(
    open val valueName: String,
    open val value: String,
    open val lastUpdateDateMillis: Long,
    open val nextUpdateDateSeconds: Long
) {
    data class FearAndGreedIndexSuccess(
        override val valueName: String,
        override val value: String,
        override val lastUpdateDateMillis: Long,
        override val nextUpdateDateSeconds: Long
    ) : FearAndGreedIndex(valueName, value, lastUpdateDateMillis, nextUpdateDateSeconds)
    object FearAndGreedIndexEmpty : FearAndGreedIndex("", "", -1, -1)
    object FearAndGreedIndexError : FearAndGreedIndex("", "", -1, -1)
}
