package app.coinfo.fngindex.di

import android.content.Context
import androidx.work.WorkManager
import app.coinfo.fngindex.net.AlternativeService
import app.coinfo.fngindex.repo.FearAndGreedIndexRepository
import app.coinfo.fngindex.repo.Repository
import app.coinfo.fngindex.wigdet.prefs.Preferences
import app.coinfo.fngindex.wigdet.prefs.WidgetPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesPreferences(
        @ApplicationContext appContext: Context,
    ): Preferences = WidgetPreferences(appContext)

    @Provides
    @Singleton
    fun providesWorkManager(
        @ApplicationContext appContext: Context,
    ): WorkManager = WorkManager.getInstance(appContext)

    @Provides
    @Singleton
    fun providesRepository(
        service: AlternativeService,
    ): Repository = FearAndGreedIndexRepository(
        service = service,
    )

    private const val ENDPOINT = "https://api.alternative.me/"
}
