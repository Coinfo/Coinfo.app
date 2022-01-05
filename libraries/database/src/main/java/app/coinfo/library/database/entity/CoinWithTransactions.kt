package app.coinfo.library.database.entity

import androidx.room.Embedded
import androidx.room.Relation

internal data class CoinWithTransactions(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "coinId",
        entityColumn = "coinCreatorId"
    )
    val transactions: List<Transaction>
)
