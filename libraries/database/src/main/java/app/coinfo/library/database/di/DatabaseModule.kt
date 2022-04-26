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

package app.coinfo.library.database.di

import android.content.Context
import androidx.room.Room
import app.coinfo.library.database.Database
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    private const val DATABASE_NAME = "coinfo.db"

    @Provides
    @Singleton
    internal fun providesRoomDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        Database::class.java, DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesPortfoliosDao(
        database: Database
    ): PortfoliosDao = database.portfoliosDao()

    @Provides
    @Singleton
    fun providesTransactionsDao(
        database: Database
    ): TransactionsDao = database.transactionsDao()
}
