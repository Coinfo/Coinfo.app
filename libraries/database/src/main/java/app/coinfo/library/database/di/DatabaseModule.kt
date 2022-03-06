package app.coinfo.library.database.di

import android.content.Context
import androidx.room.Room
import app.coinfo.library.database.Database
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    private const val DATABASE_NAME = "coinfo.db"

    @Provides
    @Singleton
    internal fun providesRoomDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        Database::class.java, DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesPortfoliosDao(
        database: Database
    ): PortfoliosDao = database.portfoliosDao()

    @Provides
    @Singleton
    fun providesTransactionsDao(
        database: Database
    ): TransactionsDao = database.transactionsDao()
}
