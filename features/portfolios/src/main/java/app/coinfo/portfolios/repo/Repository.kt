package app.coinfo.portfolios.repo

import java.io.InputStream

interface Repository {

    fun readCryptoComAppCsv(stream: InputStream?)
}
