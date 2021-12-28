package app.coinfo.portfolios.di

import app.coinfo.portfolios.repo.PortfolioRepository
import app.coinfo.portfolios.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PortfolioModule {

    @Provides
    @Singleton
    fun providesPortfolioRepository(): Repository = PortfolioRepository()
}
