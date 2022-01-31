package app.coinfo.library.preferences.di

import android.content.Context
import app.coinfo.library.preferences.CoinfoPreferences
import app.coinfo.library.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferences(
        @ApplicationContext appContext: Context
    ): Preferences = CoinfoPreferences(appContext)
}
