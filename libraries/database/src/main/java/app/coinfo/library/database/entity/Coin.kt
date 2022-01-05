package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** The database table which keeps information about coins */
@Entity(tableName = "coins")
internal data class Coin(
    /** The unique id of the coin */
    @PrimaryKey(autoGenerate = false) val coinId: String,

    /** The name for the coin */
    @ColumnInfo(name = "name") val name: String = ""
)
