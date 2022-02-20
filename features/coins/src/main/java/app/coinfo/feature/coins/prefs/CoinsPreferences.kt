package app.coinfo.feature.coins.prefs

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class CoinsPreferences @Inject constructor(
    @ApplicationContext appContext: Context
) {

    private val preferences = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** Returns current favorites mode; true - if active; otherwise false. */
    fun isFavoritesModeActive() = preferences.getBoolean(KEY_FAVORITES_MODE_ACTIVE, false)

    /** Sets the favorites mode to the opposite of [isFavoritesModeActive] returned value */
    fun switchFavoritesMode(): Boolean {
        val switchedMode = !isFavoritesModeActive()
        preferences.edit {
            putBoolean(KEY_FAVORITES_MODE_ACTIVE, switchedMode)
        }
        return switchedMode
    }

    companion object {
        private const val PREFERENCES_NAME = "coins.prefs"
        private const val KEY_FAVORITES_MODE_ACTIVE = "key_favorites_mode_active"
    }
}
