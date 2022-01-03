package app.coinfo.portfolios.repo

import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

class PortfolioRepository : Repository {

    override fun readCryptoComAppCsv(stream: InputStream?) {
        val csvReader = CSVReader(InputStreamReader(stream))
        var vv: Array<String>? = null
        while (csvReader.readNext().also { vv = it } != null) {
            // Timestamp (UTC)

            // Transaction Description

            // Currency

            // Amount

            // To Currency

            // To Amount

            // Native Currency

            // Native Amount

            // Native Amount (in USD)

            // Transaction Kind
        }
    }
}
