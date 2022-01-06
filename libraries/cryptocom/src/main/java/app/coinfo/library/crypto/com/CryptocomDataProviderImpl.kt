package app.coinfo.library.crypto.com

import app.coinfo.library.crypto.com.model.CryptocomTransactionData
import app.coinfo.library.crypto.com.model.CryptocomTransactionType
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class CryptocomDataProviderImpl : CryptocomDataProvider {

    override suspend fun readData(stream: InputStream?) = withContext(Dispatchers.Default) {
        val result = mutableListOf<CryptocomTransactionData>()
        stream?.let {
            csvReader().openAsync(it) {
                readAllAsSequence().forEach { row ->
                    result.add(
                        CryptocomTransactionData(
                            timestamp = row[INDEX_TIMESTAMP_UTC],
                            description = row[INDEX_TRANSACTION_DESCRIPTION],
                            currency = row[INDEX_CURRENCY],
                            amount = row[INDEX_AMOUNT].toDouble(),
                            toCurrency = row[INDEX_TO_CURRENCY],
                            toAmount = row[INDEX_TO_AMOUNT].toDouble(),
                            nativeCurrency = row[INDEX_NATIVE_CURRENCY],
                            nativeAmount = row[INDEX_NATIVE_AMOUNT].toDouble(),
                            nativeAmountUsd = row[INDEX_NATIVE_AMOUNT_USD].toDouble(),
                            transactionType = CryptocomTransactionType.valueOf(row[INDEX_TRANSACTION_KIND])
                        )
                    )
                }
            }
        }
        return@withContext result
    }

    companion object {
        private const val INDEX_TIMESTAMP_UTC = 0
        private const val INDEX_TRANSACTION_DESCRIPTION = 1
        private const val INDEX_CURRENCY = 2
        private const val INDEX_AMOUNT = 3
        private const val INDEX_TO_CURRENCY = 4
        private const val INDEX_TO_AMOUNT = 5
        private const val INDEX_NATIVE_CURRENCY = 6
        private const val INDEX_NATIVE_AMOUNT = 7
        private const val INDEX_NATIVE_AMOUNT_USD = 8
        private const val INDEX_TRANSACTION_KIND = 9
    }
}
