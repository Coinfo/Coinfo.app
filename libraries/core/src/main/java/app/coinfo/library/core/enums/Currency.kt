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

package app.coinfo.library.core.enums

import androidx.annotation.StringRes
import app.coinfo.library.core.R

/** Enumeration of currency codes and symbols. */
enum class Currency(
    val uuid: String,
    val code: String,
    val symbol: String,
    @StringRes val resId: Int,
) {
    EUR("466a214c-9022-11ec-b909-0242ac120002", "eur", "€", R.string.core_currency_eur),
    USD("466a23fe-9022-11ec-b909-0242ac120002", "usd", "$", R.string.core_currency_usd),
    AMD("bbbb3eb6-a5ff-11ec-b909-0242ac120002", "amd", "֏", R.string.core_currency_amd),
    RUB("bbbb4230-a5ff-11ec-b909-0242ac120002", "rub", "₽", R.string.core_currency_rub),
    NA("466a253e-9022-11ec-b909-0242ac120002", "N/A", "", R.string.core_currency_na);

    companion object {
        /** Returns symbol for the given currency code */
        fun toSymbol(code: String?) = values().firstOrNull { it.code.equals(code, true) }?.symbol ?: NA.symbol
        /** Returns code for the given currency symbol */
        fun toCode(symbol: String?) = values().firstOrNull { it.symbol == symbol }?.code ?: NA.code
        /** Returns [TimeInterval] for given [uuid], if conversion fails returns [DAY] */
        fun fromUUID(uuid: String?) = values().firstOrNull { it.uuid == uuid } ?: EUR
    }
}
