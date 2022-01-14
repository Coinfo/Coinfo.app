package app.coinfo.library.cloud.di

import app.coinfo.library.cloud.Cloud
import app.coinfo.library.cloud.CoinfoCloud
import app.coinfo.library.cloud.service.CoingeckoService
import app.coinfo.library.logger.Logger
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
    ): Cloud = CoinfoCloud(
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CoingeckoService.ENDPOINT)
            .build()
            .create(CoingeckoService::class.java),
        logger
    )
}
