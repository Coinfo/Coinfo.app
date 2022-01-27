package app.coinfo.feature.coins.di

import app.coinfo.feature.coins.repos.CoinsRepository
import app.coinfo.feature.coins.repos.CoinsRepositoryImpl
import app.coinfo.library.cloud.Cloud
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoinsModule {

    @Provides
    @Singleton
    fun providesCoinsRepository(
        cloud: Cloud,
    ): CoinsRepository = CoinsRepositoryImpl(cloud)
}
