package app.coinfo.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.coinfo.library.database.entity.Coin
import app.coinfo.library.database.entity.CoinWithTransactions
import app.coinfo.library.database.entity.Portfolio
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CoinsDao {

    @Transaction
    @Query("SELECT * FROM coins")
    suspend fun getCoinsWithTransactions(): List<CoinWithTransactions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: app.coinfo.library.database.entity.Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoin(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPortfolio(portfolio: Portfolio): Long

    @Query("SELECT * FROM portfolios ORDER BY creationDate ASC")
    fun getPortfolios(): Flow<List<Portfolio>>
}
