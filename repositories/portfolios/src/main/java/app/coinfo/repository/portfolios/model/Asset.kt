package app.coinfo.repository.portfolios.model

data class Asset(
    val coinId: String,
    val portfolioId: Long,
    val transactions: List<Transaction>,
) {

    fun getAssetAmount(): Double {
        var amount = 0.0
        transactions.forEach {
            amount += it.amount
        }
        return amount
    }
}
