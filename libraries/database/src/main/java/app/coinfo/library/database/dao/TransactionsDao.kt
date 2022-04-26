/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @Query("DELETE FROM transactions WHERE portfolio_id = :portfolioId")
    suspend fun deleteTransactionsWithPortfolioId(portfolioId: Long)
}
