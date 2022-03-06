package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val id: Long = 0L,

    @ColumnInfo(name = "coin_id")
    val coinId: String,

    @ColumnInfo(name = "portfolio_id")
    val portfolioId: Long,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "price_per_coin")
    val pricePerCoin: Double,

    @ColumnInfo(name = "symbol")
    val symbol: String,
)
