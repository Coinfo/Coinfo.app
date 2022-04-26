/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
