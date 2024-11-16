package com.miso.vinilos.database.dao

import androidx.room.*
import com.miso.vinilos.models.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll():Int
}