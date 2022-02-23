package app.coinfo.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.coinfo.library.database.entity.Portfolio
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PortfoliosDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPortfolio(portfolio: Portfolio): Long

    @Query("SELECT * FROM portfolios ORDER BY creationDate ASC")
    fun getPortfolios(): Flow<List<Portfolio>>
}
