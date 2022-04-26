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

import app.coinfo.library.database.dao.PortfoliosDao
import app.coinfo.library.database.dao.TransactionsDao
import app.coinfo.library.database.entity.PortfolioEntity
import app.coinfo.library.database.entity.TransactionEntity
import app.coinfo.library.database.enums.PortfolioType
import app.coinfo.repository.portfolios.model.Asset
import app.coinfo.repository.portfolios.model.Assets
import app.coinfo.repository.portfolios.model.Portfolio
import app.coinfo.repository.portfolios.model.Transaction

internal class PortfoliosRepositoryImpl(
    private val portfoliosDao: PortfoliosDao,
    private val transactionsDao: TransactionsDao,
) : PortfoliosRepository {

    override suspend fun createPortfolio(name: String) {
        portfoliosDao.insert(
            PortfolioEntity(
                name = name, date = System.currentTimeMillis(), type = PortfolioType.MANUAL
            )
        )
    }

    override suspend fun loadPortfolios(): List<Portfolio> =
        portfoliosDao.loadPortfolios().map {
            Portfolio(id = it.id, name = it.name, loadAssets(it.id))
        }

    override suspend fun loadPortfolio(id: Long, includeAssets: Boolean) = with(portfoliosDao.loadPortfolio(id)) {
        Portfolio(id = id, name = name, if (includeAssets) loadAssets(id) else Assets(emptyList(), emptyList()))
    }

    override suspend fun deletePortfolio(id: Long) {
        portfoliosDao.deletePortfolio(id)
        transactionsDao.deleteTransactionsWithPortfolioId(id)
    }

    override suspend fun editPortfolio(portfolioId: Long, portfolioName: String) {
        portfoliosDao.updatePortfolio(portfolioId, portfolioName)
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionsDao.insert(
            TransactionEntity(
                coinId = transaction.coinId,
                portfolioId = transaction.portfolioId,
                amount = transaction.amount,
                pricePerCoin = transaction.pricePerCoin,
                symbol = transaction.symbol,
                fee = transaction.fee,
                currency = transaction.currency,
                transactionType = transaction.type,
                date = transaction.date,
                notes = transaction.notes,
            )
        )
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionsDao.update(
            TransactionEntity(
                id = transaction.id,
                coinId = transaction.coinId,
                portfolioId = transaction.portfolioId,
                amount = transaction.amount,
                pricePerCoin = transaction.pricePerCoin,
                symbol = transaction.symbol,
                fee = transaction.fee,
                currency = transaction.currency,
                transactionType = transaction.type,
                date = transaction.date,
                notes = transaction.notes,
            )
        )
    }

    override suspend fun loadTransactions(portfolioId: Long, coinId: String) =
        transactionsDao.loadTransactions(portfolioId, coinId).map {
            Transaction(
                id = it.id,
                coinId = it.coinId,
                portfolioId = it.portfolioId,
                symbol = it.symbol,
                amount = it.amount,
                pricePerCoin = it.pricePerCoin,
                fee = it.fee,
                currency = it.currency,
                type = it.transactionType,
                date = it.date,
                notes = it.notes,
            )
        }

    override suspend fun loadTransaction(id: Long) =
        with(transactionsDao.loadTransaction(id)) {
            return@with Transaction(
                coinId = coinId,
                portfolioId = portfolioId,
                symbol = symbol,
                amount = amount,
                pricePerCoin = pricePerCoin,
                fee = fee,
                currency = currency,
                type = transactionType,
                date = date,
                notes = notes,
            )
        }

    override suspend fun deleteTransaction(id: Long) {
        transactionsDao.deleteTransaction(id)
    }

    override suspend fun loadAssets(portfolioId: Long): Assets {
        val transactions = mutableMapOf<String, MutableList<Transaction>>()
        transactionsDao.loadTransactions(portfolioId).forEach {
            val transaction = transactions[it.coinId]
            if (transaction == null) {
                transactions[it.coinId] = mutableListOf()
            }
            transactions[it.coinId]!!.add(
                Transaction(
                    coinId = it.coinId,
                    portfolioId = it.portfolioId,
                    symbol = it.symbol,
                    amount = it.amount,
                    pricePerCoin = it.pricePerCoin,
                    fee = it.fee,
                    currency = it.currency,
                    type = it.transactionType,
                    date = it.date,
                    notes = it.notes,
                )
            )
        }
        return Assets(transactions.map { Asset(it.key, portfolioId, it.value) }, transactions.keys.toList())
    }
}
