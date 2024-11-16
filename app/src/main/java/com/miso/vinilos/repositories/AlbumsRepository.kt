package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.database.VinylRoomDatabase
import com.miso.vinilos.models.Album
import com.miso.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsRepository(private val application: Application) {

    private val albumDao = VinylRoomDatabase.getDatabase(application).albumsDao()

    fun refreshData(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cachedAlbums = albumDao.getAlbums()
                if (cachedAlbums.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        callback(cachedAlbums)
                    }
                } else {
                    fetchAlbumsFromNetwork(callback, onError)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(VolleyError(e))
                }
            }
        }
    }

    private fun fetchAlbumsFromNetwork(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums(
            { albums ->
                CoroutineScope(Dispatchers.IO).launch {
                    albumDao.deleteAll()
                    albums.forEach { album ->
                        albumDao.insert(album)
                    }
                    withContext(Dispatchers.Main) {
                        callback(albums)
                    }
                }
            },
            onError
        )
    }
}