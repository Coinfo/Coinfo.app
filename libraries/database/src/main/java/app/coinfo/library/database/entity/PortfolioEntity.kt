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

package app.coinfo.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.coinfo.library.database.converter.Converters
import app.coinfo.library.database.enums.PortfolioType

@Entity(tableName = "portfolios")
data class PortfolioEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "portfolio_id")
    val id: Long = 0L,

    @ColumnInfo(name = "portfolio_name")
    val name: String,

    @ColumnInfo(name = "portfolio_creation_date")
    val date: Long,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "portfolio_type")
    val type: PortfolioType
)
