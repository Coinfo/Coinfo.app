package app.coinfo.library.cloud.enums

/** Enumeration of supported time intervals. */
enum class TimeInterval {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    TWO_MONTHS,
    YEAR;

    companion object {
        private const val ONE_DAY = 1
        private const val SEVEN_DAYS = 7
        private const val THIRTY_DAYS = 30
        private const val SIXTY_DAYS = 60
        private const val THREE_HUNDRED_SIXTY_FIVE_DAYS = 365

        /** Gets number of days for given [interval] */
        fun toDays(interval: TimeInterval) = when (interval) {
            HOUR, DAY -> ONE_DAY
            WEEK -> SEVEN_DAYS
            MONTH -> THIRTY_DAYS
            TWO_MONTHS -> SIXTY_DAYS
            YEAR -> THREE_HUNDRED_SIXTY_FIVE_DAYS
        }
    }
}
