package com.eastx7.weather.data.db

import com.eastx7.weather.data.db.DbDay
import kotlinx.coroutines.flow.Flow
import androidx.room.*

@Dao
interface DbDayDao {

    @Query(
        "SELECT * " +
                "FROM days " +
                "WHERE days.id = :id"
    )
    fun getById(id: Int): DbDay?

    @Query("SELECT * FROM days ORDER BY epoch DESC LIMIT :numDays")
    fun listOfItems(numDays: Int): Flow<List<DbDay>>

    @Query("DELETE FROM days")
    fun deleteAll(): Int

    @Query("DELETE FROM days WHERE id = :id")
    fun deleteById(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbDay: DbDay): Long // returns id

    @Update
    suspend fun update(dbDay: DbDay): Int //returns qnt updated items

    @Delete
    suspend fun delete(dbDay: DbDay)
}
