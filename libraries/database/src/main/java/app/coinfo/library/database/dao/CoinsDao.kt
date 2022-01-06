package app.coinfo.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.coinfo.library.database.entity.Coin

@Dao
internal interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoin(coin: Coin)
}
