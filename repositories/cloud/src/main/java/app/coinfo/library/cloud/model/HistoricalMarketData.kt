package app.coinfo.library.cloud.model

data class HistoricalMarketData(
    val prices: List<PriceDatePair>,
    val marketCaps: List<PriceDatePair>,
)
