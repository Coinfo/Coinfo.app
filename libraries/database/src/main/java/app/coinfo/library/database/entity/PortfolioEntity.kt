package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.coinfo.library.database.converter.Converters
import app.coinfo.library.database.enums.PortfolioType

@Entity(tableName = "portfolios")
data class PortfolioEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "portfolio_id")
    val id: Long = 0L,

    @ColumnInfo(name = "portfolio_name")
    val name: String,

    @ColumnInfo(name = "portfolio_creation_date")
    val date: Long,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "portfolio_type")
    val type: PortfolioType
)
