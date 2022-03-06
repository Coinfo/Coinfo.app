package app.coinfo.repository.portfolios.di

import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.repository.portfolios.PortfoliosRepository
import app.coinfo.repository.portfolios.PortfoliosRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PortfoliosRepositoryModule {

    @Provides
    @Singleton
    fun providesPortfoliosRepository(
        portfoliosDao: PortfoliosDao,
        transactionsDao: TransactionsDao,
    ): PortfoliosRepository = PortfoliosRepositoryImpl(portfoliosDao, transactionsDao)
}
