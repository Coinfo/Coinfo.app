package app.coinfo.library.database.model

data class PortfolioData(
    val id: Long = 0L,
    val name: String,
    val source: String,
    val date: Long,
)
