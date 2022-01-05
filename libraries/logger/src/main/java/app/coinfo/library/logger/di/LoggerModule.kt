package app.coinfo.library.logger.di

import app.coinfo.library.logger.CoinfoLogger
import app.coinfo.library.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LoggerModule {

    @Provides
    @Singleton
    fun providesLogger(): Logger = CoinfoLogger()
}
