package app.coinfo.fngindex.wigdet.model

data class DailyFearAndGreedIndex(
    /** Value name of daily fear and greed index. */
    val valueName: String,
    /** Value of daily fear and greed index. */
    val value: Int,
    /** Time when daily fear and greed index was last time updated. */
    val lastUpdateDateMillis: Long,
    /**
     * Time in seconds when next update will happen, this time should be calculated
     * starting from [timestampInMillis].
     */
    val nextUpdateDateSeconds: Int,
    /** Time when daily fear and greed index object was created. */
    val timestampInMillis: Long,
)
