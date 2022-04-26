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

package app.coinfo.library.database.converter

import androidx.room.TypeConverter
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TransactionType
import app.coinfo.library.database.enums.PortfolioType

class Converters {

    @TypeConverter
    fun toPortfolioType(value: String) = enumValueOf<PortfolioType>(value)

    @TypeConverter
    fun fromPortfolioType(value: PortfolioType) = value.name

    @TypeConverter
    fun toCurrency(value: String) = enumValueOf<Currency>(value)

    @TypeConverter
    fun fromCurrency(value: Currency) = value.uuid

    @TypeConverter
    fun toTransactionType(value: String) = enumValueOf<TransactionType>(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType) = value.uuid
}
