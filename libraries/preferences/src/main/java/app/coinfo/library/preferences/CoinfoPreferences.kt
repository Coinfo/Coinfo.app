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

import android.content.Context
import androidx.core.content.edit
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.enums.TimeInterval

class CoinfoPreferences(
    appContext: Context
) : Preferences {

    private val sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveCurrency(currency: Currency) {
        sharedPreferences.edit().putString(KEY_CURRENCY, currency.uuid).apply()
    }

    override fun loadCurrency() = Currency.fromUUID(
        sharedPreferences.getString(KEY_CURRENCY, Currency.EUR.uuid)
    )

    override fun saveTimeInterval(interval: TimeInterval) {
        sharedPreferences.edit().putString(KEY_CHANGE_TIMELINE, interval.uuid).apply()
    }

    override fun loadTimeInterval() = TimeInterval.fromUUID(
        sharedPreferences.getString(KEY_CHANGE_TIMELINE, TimeInterval.DAY.uuid)
    )

    override fun saveFavoriteCoin(id: String) {
        val idsToSave = mutableSetOf<String>()
        val ids: MutableSet<String>? = sharedPreferences.getStringSet(KEY_FAVORITE_COIN_IDS, emptySet())
        // Adds new id to the existing set of ids.
        ids?.forEach { idsToSave.add(it) }
        idsToSave.add(id)
        sharedPreferences.edit { putStringSet(KEY_FAVORITE_COIN_IDS, idsToSave) }
    }

    override fun removeFavorite(id: String) {
        val idsToSave = mutableSetOf<String>()
        val ids: MutableSet<String>? = sharedPreferences.getStringSet(KEY_FAVORITE_COIN_IDS, emptySet())
        ids?.forEach { idsToSave.add(it) }
        idsToSave.remove(id)
        sharedPreferences.edit { putStringSet(KEY_FAVORITE_COIN_IDS, idsToSave) }
    }

    override fun loadFavoriteCoins() =
        sharedPreferences.getStringSet(KEY_FAVORITE_COIN_IDS, emptySet())?.toList() ?: emptyList()

    override fun isCoinFavorite(id: String) = sharedPreferences.getStringSet(
        KEY_FAVORITE_COIN_IDS, emptySet()
    )?.contains(id) ?: false

    companion object {
        private const val SHARED_PREFERENCES_NAME = "coinfo.prefs"
        private const val KEY_CURRENCY = "key_currency"
        private const val KEY_CHANGE_TIMELINE = "key_time_interval"
        private const val KEY_FAVORITE_COIN_IDS = "key_favorite_coin_ids"
    }
}
