package app.coinfo.library.crypto.com

import app.coinfo.library.crypto.com.model.CryptocomTransactionData
import java.io.InputStream

interface CryptocomDataProvider {

    /** Reads data from scv stream. */
    suspend fun readData(stream: InputStream?): List<CryptocomTransactionData>
}
