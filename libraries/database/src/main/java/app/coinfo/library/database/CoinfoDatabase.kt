package app.coinfo.library.database

import android.content.Context

internal class CoinfoDatabase(
    private val appContext: Context
) : Database {

    override suspend fun savePortfolio(name: String) {
        val a = appContext
        a.applicationContext
    }
}
