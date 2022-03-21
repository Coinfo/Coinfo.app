package app.coinfo.library.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.library.database.entity.PortfolioEntity
import app.coinfo.library.database.entity.TransactionEntity

@Database(entities = [PortfolioEntity::class, TransactionEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun portfoliosDao(): PortfoliosDao

    abstract fun transactionsDao(): TransactionsDao
}
