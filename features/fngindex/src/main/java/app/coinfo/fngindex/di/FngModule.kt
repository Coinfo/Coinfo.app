package app.coinfo.fngindex.di

import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.repo.FearAndGreedIndexRepository
import app.coinfo.fngindex.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FngModule {

    @Provides
    @Singleton
    fun providesService() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ENDPOINT)
        .build()
        .create(AlternativeService::class.java)

    @Provides
    @Singleton
    fun providesRepository(
        service: AlternativeService
    ): Repository = FearAndGreedIndexRepository(service)

    private const val ENDPOINT = "https://api.alternative.me/"
}
