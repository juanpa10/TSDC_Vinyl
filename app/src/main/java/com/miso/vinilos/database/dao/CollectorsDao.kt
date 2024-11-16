package com.miso.vinilos.database.dao

import androidx.room.*
import com.miso.vinilos.models.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: Collector)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll():Int
}