package app.coinfo.portfolios.di

import app.coinfo.library.database.Database
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.repo.PortfolioRepository
import app.coinfo.portfolios.repo.Repository
import app.coinfo.portfolios.ui.adapter.PortfolioAdapter
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
    fun providesPortfolioRepository(
        database: Database,
    ): Repository = PortfolioRepository(database)

    @Provides
    @Singleton
    fun providesPortfoliosAdapter(
        repository: Repository,
        logger: Logger,
    ): PortfolioAdapter = PortfolioAdapter(repository, logger)
}
