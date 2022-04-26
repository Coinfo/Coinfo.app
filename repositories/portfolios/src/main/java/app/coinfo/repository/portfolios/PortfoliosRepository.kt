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

package app.coinfo.repository.portfolios

import app.coinfo.repository.portfolios.model.Assets
import app.coinfo.repository.portfolios.model.Portfolio
import app.coinfo.repository.portfolios.model.Transaction

interface PortfoliosRepository {

    /**
     * Creates portfolio and saves it to the database.
     *
     * @param name The name of the portfolio
     **/
    suspend fun createPortfolio(name: String)

    suspend fun loadPortfolios(): List<Portfolio>

    suspend fun loadPortfolio(id: Long, includeAssets: Boolean = false): Portfolio

    suspend fun deletePortfolio(id: Long)

    suspend fun editPortfolio(portfolioId: Long, portfolioName: String)

    /**
     * Adds transaction
     *
     * @param transaction The transaction to be added.
     */
    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun loadTransactions(portfolioId: Long, coinId: String): List<Transaction>

    suspend fun loadTransaction(id: Long): Transaction

    suspend fun deleteTransaction(id: Long)

    suspend fun loadAssets(portfolioId: Long): Assets
}
