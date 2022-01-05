package app.coinfo.library.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.coinfo.library.database.dao.CoinsDao
import app.coinfo.library.database.entity.Coin
import app.coinfo.library.database.entity.Portfolio
import app.coinfo.library.database.entity.Transaction

@Database(entities = [Coin::class, Transaction::class, Portfolio::class], version = 2)
internal abstract class CoinfoRoomDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao

    companion object {
        const val DATABASE_NAME = "coinfo.db"
    }
}
