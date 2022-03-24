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
}
