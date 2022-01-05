package app.coinfo.portfolios.repo

import java.io.InputStream

interface Repository {

    suspend fun readCryptoComAppCsv(filename: String?, stream: InputStream?)
}
