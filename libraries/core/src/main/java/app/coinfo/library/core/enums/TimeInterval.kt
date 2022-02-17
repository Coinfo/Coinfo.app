package app.coinfo.library.core.enums

enum class TimeInterval(
    val uuid: String,
    val numberInDays: Int,
) {
    HOUR("95930c70-9022-11ec-b909-0242ac120002", 0),
    DAY("95930eb4-9022-11ec-b909-0242ac120002", 1),
    WEEK("95930fea-9022-11ec-b909-0242ac120002", 7),
    MONTH("95931242-9022-11ec-b909-0242ac120002", 30),
    TWO_MONTHS("9593136e-9022-11ec-b909-0242ac120002", 60),
    YEAR("95931490-9022-11ec-b909-0242ac120002", 365);
}
