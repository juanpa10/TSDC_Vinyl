package com.miso.vinilos.database.dao

import androidx.room.*
import com.miso.vinilos.models.Comment

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments_table")
    fun getComments():List<Comment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment)

    @Query("DELETE FROM comments_table")
    suspend fun deleteAll():Int
}