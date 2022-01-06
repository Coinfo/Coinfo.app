package app.coinfo.portfolios.di

import app.coinfo.library.database.Database
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.repo.PortfolioRepository
import app.coinfo.portfolios.repo.Repository
import app.coinfo.portfolios.ui.adapter.PortfolioAdapter
import app.coinfo.portfolios.ui.adapter.PortfolioDetailsAdapter
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
        logger: Logger,
    ): Repository = PortfolioRepository(database, logger)

    @Provides
    @Singleton
    fun providesPortfoliosAdapter(
        repository: Repository,
        logger: Logger,
    ): PortfolioAdapter = PortfolioAdapter(repository, logger)

    @Provides
    @Singleton
    fun providesPortfolioDetailsAdapter(
        repository: Repository,
        logger: Logger,
    ): PortfolioDetailsAdapter = PortfolioDetailsAdapter(repository, logger)
}
