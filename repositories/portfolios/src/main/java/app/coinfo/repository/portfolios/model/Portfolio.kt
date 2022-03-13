package app.coinfo.repository.portfolios.model

data class Portfolio(
    val id: Long,
    val name: String,
    val assets: Assets,
)
