package app.coinfo.library.database.di

import android.content.Context
import androidx.room.Room
import app.coinfo.library.database.CoinfoDatabase
import app.coinfo.library.database.Database
import app.coinfo.library.database.database.CoinfoRoomDatabase
import app.coinfo.library.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext appContext: Context,
        logger: Logger
    ): Database = CoinfoDatabase(
        Room.databaseBuilder(
            appContext,
            CoinfoRoomDatabase::class.java, CoinfoRoomDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build(),
        logger
    )
}
