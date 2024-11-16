package com.miso.vinilos.database.dao

import androidx.room.*
import com.miso.vinilos.models.Band

@Dao
interface BandsDao {
    @Query("SELECT * FROM bands_table")
    fun getBands():List<Band>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(band: Band)

    @Query("DELETE FROM bands_table")
    suspend fun deleteAll():Int
}