package app.coinfo.fngindex.model

data class FearAndGreedIndex(
    val valueName: String,
    val value: String,
    val lastUpdateDateMillis: Long,
    val nextUpdateDateSeconds: Long,
)
