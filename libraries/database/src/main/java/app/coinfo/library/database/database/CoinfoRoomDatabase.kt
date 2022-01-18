package app.coinfo.library.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.coinfo.library.database.dao.CoinsDao
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.library.database.entity.Coin
import app.coinfo.library.database.entity.Portfolio
import app.coinfo.library.database.entity.Transaction

@Database(entities = [Coin::class, Transaction::class, Portfolio::class], version = 5)
internal abstract class CoinfoRoomDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao

    abstract fun transactionsDao(): TransactionsDao

    abstract fun portfoliosDao(): PortfoliosDao

    companion object {
        const val DATABASE_NAME = "coinfo.db"
    }
}
