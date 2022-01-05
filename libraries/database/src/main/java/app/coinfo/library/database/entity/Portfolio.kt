package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolios")
internal data class Portfolio(

    /** The unique id of the portfolio */
    @PrimaryKey(autoGenerate = true) val portfolioId: Long = 0L,

    /** The display name of the portfolio */
    @ColumnInfo(name = "displayName") val displayName: String,

    /** The source for where portfolio was imported. */
    @ColumnInfo(name = "source") val source: String,

    /** The date when the portfolio was created */
    @ColumnInfo(name = "creationDate") val date: Long
)
