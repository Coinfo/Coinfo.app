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

package app.coinfo.library.preferences

import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

interface Preferences {

    /** Saves [Currency] to the global shared preferences. */
    fun saveCurrency(currency: Currency)

    /**
     * Loads globally saved [Currency] from shared preferences.
     * If no value previously saved returns [Currency.EUR]
     */
    fun loadCurrency(): Currency

    /** Saves [TimeInterval] to the global shared preferences. */
    fun saveTimeInterval(interval: TimeInterval)

    /**
     * Loads globally saved [TimeInterval] from shared preferences.
     * If no value previously saved returns [TimeInterval.DAY]
     */
    fun loadTimeInterval(): TimeInterval

    /** Saved [id] of the given coin to the global favorites shared preferences. */
    fun saveFavoriteCoin(id: String)

    /** Removes given [id] of favorite coin from the global shared preferences. */
    fun removeFavorite(id: String)

    /** Loads globally saved [List] of coin ids from shared preferences. */
    fun loadFavoriteCoins(): List<String>

    /** Returns true if coin is already in list of favorites; otherwise false */
    fun isCoinFavorite(id: String): Boolean
}
