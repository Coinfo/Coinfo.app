package app.coinfo.feature.coins.di

import app.coinfo.feature.coins.repos.CoinsInternalRepository
import app.coinfo.feature.coins.repos.CoinsInternalRepositoryImpl
import app.coinfo.repository.coins.CoinsRepository
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
        coinsRepository: CoinsRepository,
    ): CoinsInternalRepository = CoinsInternalRepositoryImpl(coinsRepository)
}
