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
import app.coinfo.library.database.entity.PortfolioEntity

@Dao
interface PortfoliosDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(portfolio: PortfolioEntity)

    @Query("SELECT * FROM portfolios ORDER BY portfolio_creation_date DESC")
    suspend fun loadPortfolios(): List<PortfolioEntity>

    @Query("SELECT * FROM portfolios WHERE portfolio_id=:id")
    suspend fun loadPortfolio(id: Long): PortfolioEntity

    @Query("DELETE FROM portfolios WHERE portfolio_id=:id")
    suspend fun deletePortfolio(id: Long)

    @Query("UPDATE portfolios SET portfolio_name=:portfolioName WHERE portfolio_id=:portfolioId")
    suspend fun updatePortfolio(portfolioId: Long, portfolioName: String)
}
