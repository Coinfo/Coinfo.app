package app.coinfo.repository.coins.model

data class HistoricalMarketData(
    val prices: List<PriceDatePair>,
    val marketCaps: List<PriceDatePair>,
)
