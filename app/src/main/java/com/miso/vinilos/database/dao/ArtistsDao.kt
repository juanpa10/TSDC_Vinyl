package com.miso.vinilos.database.dao

import androidx.room.*
import com.miso.vinilos.models.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artist_table")
    fun getArtists():List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: Artist)

    @Query("DELETE FROM artist_table")
    suspend fun deleteAll():Int
}