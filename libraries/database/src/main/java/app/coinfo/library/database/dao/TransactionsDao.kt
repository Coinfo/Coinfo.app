package app.coinfo.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.coinfo.library.database.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE portfolioCreatorId=:portfolioId")
    fun getTransactions(portfolioId: Long): Flow<List<Transaction>>

    @Query(
        """SELECT * FROM transactions
                    WHERE portfolioCreatorId=:portfolioId AND coinCreatorId=:assetId AND (
                        type=0 OR type=1 OR type=3 OR type=4
                    )
                    ORDER BY date DESC"""
    )
    fun getTransactions(portfolioId: Long, assetId: String): Flow<List<Transaction>>
}
