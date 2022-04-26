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

package app.coinfo.library.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.library.database.entity.PortfolioEntity
import app.coinfo.library.database.entity.TransactionEntity

@Database(entities = [PortfolioEntity::class, TransactionEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun portfoliosDao(): PortfoliosDao

    abstract fun transactionsDao(): TransactionsDao
}
