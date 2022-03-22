package app.coinfo.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.coinfo.library.database.entity.TransactionEntity

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transaction: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: TransactionEntity)

    /**
     * Loads all transactions
     *
     * @param portfolioId The portfolio id for which transactions should be loaded.
     */
    @Query("SELECT * FROM transactions WHERE portfolio_id = :portfolioId")
    suspend fun loadTransactions(portfolioId: Long): List<TransactionEntity>

    @Query(
        "SELECT * FROM transactions " +
            "WHERE (portfolio_id = :portfolioId AND coin_id = :coinId) ORDER BY date DESC"
    )
    suspend fun loadTransactions(portfolioId: Long, coinId: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE transaction_id = :transactionId")
    suspend fun loadTransaction(transactionId: Long): TransactionEntity

    @Query("DELETE FROM transactions WHERE transaction_id = :transactionId")
    suspend fun deleteTransaction(transactionId: Long)
}
