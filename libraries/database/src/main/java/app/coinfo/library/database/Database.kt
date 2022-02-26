package app.coinfo.library.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.entity.PortfolioEntity

@Database(entities = [PortfolioEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun portfoliosDao(): PortfoliosDao
}
