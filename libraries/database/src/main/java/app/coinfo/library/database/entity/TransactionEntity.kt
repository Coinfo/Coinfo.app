package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.database.converter.Converters

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

    @ColumnInfo(name = "fee")
    val fee: Double,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "currency")
    val currency: Currency,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,

    @ColumnInfo(name = "date")
    val date: Long
)
