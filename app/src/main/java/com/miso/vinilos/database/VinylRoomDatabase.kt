package com.miso.vinilos.database
import android.content.Context
import androidx.room.*
import com.miso.vinilos.database.dao.AlbumsDao
import com.miso.vinilos.database.dao.ArtistsDao
import com.miso.vinilos.database.dao.BandsDao
import com.miso.vinilos.database.dao.CollectorsDao
import com.miso.vinilos.database.dao.CommentsDao
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Band
import com.miso.vinilos.models.Collector
import com.miso.vinilos.models.Comment

@Database(entities = [Album::class, Collector::class, Comment::class, Artist::class, Band::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun collectorsDao(): CollectorsDao
    abstract fun commentsDao(): CommentsDao
    abstract  fun artistsDao() : ArtistsDao
    abstract  fun bandsDao(): BandsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}