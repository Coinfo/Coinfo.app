package app.coinfo.fngindex.wigdet.model

data class DailyFearAndGreedIndex(
    val valueName: String,
    val value: Int,
    val lastUpdateDateMillis: Long,
    val nextUpdateDateSeconds: Int
)
