package app.coinfo.repository.coins.di

import app.coinfo.library.logger.Logger
import app.coinfo.repository.coins.CoinsRepository
import app.coinfo.repository.coins.CoinsRepositoryImpl
import app.coinfo.repository.coins.service.CoingeckoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CloudModule {

    @Provides
    @Singleton
    fun providesCloud(
        logger: Logger
    ): CoinsRepository = CoinsRepositoryImpl(
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CoingeckoService.ENDPOINT)
            .build()
            .create(CoingeckoService::class.java),
        logger
    )
}
