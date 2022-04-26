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
