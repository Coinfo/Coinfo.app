package app.coinfo.library.crypto.com

import android.annotation.SuppressLint
import app.coinfo.library.crypto.com.model.CryptocomTransactionData
import app.coinfo.library.crypto.com.model.CryptocomTransactionType
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.text.SimpleDateFormat

class CryptocomDataProviderImpl : CryptocomDataProvider {

    override suspend fun readData(stream: InputStream?) = withContext(Dispatchers.Default) {
        val result = mutableListOf<CryptocomTransactionData>()
        stream?.let {
            csvReader().openAsync(it) {
                readAllAsSequence().forEachIndexed { index, row ->
                    if (index == 0 && isCsvHeaderCorrect(row)) {
                        return@forEachIndexed
                    }

                    result.add(
                        CryptocomTransactionData(
                            timestamp = row[INDEX_TIMESTAMP_UTC].toDateMilliseconds(),
                            description = row[INDEX_TRANSACTION_DESCRIPTION],
                            currency = row[INDEX_CURRENCY],
                            amount = row[INDEX_AMOUNT].toSafeDouble(),
                            toCurrency = row[INDEX_TO_CURRENCY],
                            toAmount = row[INDEX_TO_AMOUNT].toSafeDouble(),
                            nativeCurrency = row[INDEX_NATIVE_CURRENCY],
                            nativeAmount = row[INDEX_NATIVE_AMOUNT].toSafeDouble(),
                            nativeAmountUsd = row[INDEX_NATIVE_AMOUNT_USD].toSafeDouble(),
                            transactionType = CryptocomTransactionType.fromString(row[INDEX_TRANSACTION_KIND])
                        )
                    )
                }
            }
        }
        return@withContext result
    }

    private fun isCsvHeaderCorrect(row: List<String>) =
        row[INDEX_TIMESTAMP_UTC].isEquals("Timestamp (UTC)") &&
            row[INDEX_TRANSACTION_DESCRIPTION].isEquals("Transaction Description") &&
            row[INDEX_CURRENCY].isEquals("Currency") &&
            row[INDEX_AMOUNT].isEquals("Amount") &&
            row[INDEX_TO_CURRENCY].isEquals("To Currency") &&
            row[INDEX_TO_AMOUNT].isEquals("To Amount") &&
            row[INDEX_NATIVE_CURRENCY].isEquals("Native Currency") &&
            row[INDEX_NATIVE_AMOUNT].isEquals("Native Amount") &&
            row[INDEX_NATIVE_AMOUNT_USD].isEquals("Native Amount (in USD)") &&
            row[INDEX_TRANSACTION_KIND].isEquals("Transaction Kind")

    private fun String.isEquals(to: String) = this.trim().equals(to, true)

    private fun String.toSafeDouble(): Double = if (this.isBlank()) 0.0 else this.toDouble()

    @SuppressLint("SimpleDateFormat")
    private fun String.toDateMilliseconds(): Long {
        val dateFormatLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val utcDate = dateFormatLocal.parse(this)
        return utcDate?.time ?: 0
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
