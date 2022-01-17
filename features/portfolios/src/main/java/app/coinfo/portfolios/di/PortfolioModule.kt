package app.coinfo.portfolios.di

import app.coinfo.library.cloud.Cloud
import app.coinfo.library.database.Database
import app.coinfo.library.logger.Logger
import app.coinfo.portfolios.repo.asset.AssetRepository
import app.coinfo.portfolios.repo.asset.AssetRepositoryImpl
import app.coinfo.portfolios.repo.portfolio.PortfolioRepository
import app.coinfo.portfolios.repo.portfolio.PortfolioRepositoryImpl
import app.coinfo.portfolios.ui.details.portfolio.PortfolioDetailsAdapter
import app.coinfo.portfolios.ui.overview.PortfolioAdapter
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
    ): PortfolioRepository = PortfolioRepositoryImpl(database, logger)

    @Provides
    @Singleton
    fun providesAssetsRepository(
        database: Database,
        cloud: Cloud,
    ): AssetRepository = AssetRepositoryImpl(database, cloud)

    @Provides
    @Singleton
    fun providesPortfoliosAdapter(
        repository: PortfolioRepository,
        logger: Logger,
    ): PortfolioAdapter = PortfolioAdapter(repository, logger)

    @Provides
    @Singleton
    fun providesPortfolioDetailsAdapter(
        repository: PortfolioRepository,
        logger: Logger,
    ): PortfolioDetailsAdapter = PortfolioDetailsAdapter(repository, logger)
}
