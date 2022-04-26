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
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.database.converter.Converters

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val id: Long = 0L,

    @ColumnInfo(name = "coin_id")
    val coinId: String,

    @ColumnInfo(name = "portfolio_id")
    val portfolioId: Long,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "price_per_coin")
    val pricePerCoin: Double,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "fee")
    val fee: Double,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "currency")
    val currency: Currency,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "notes")
    val notes: String,
)
