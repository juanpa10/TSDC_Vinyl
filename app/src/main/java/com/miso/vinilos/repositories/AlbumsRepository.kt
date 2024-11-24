package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.database.VinylRoomDatabase
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Track
import com.miso.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsRepository(private val application: Application) {

    private val albumDao = VinylRoomDatabase.getDatabase(application).albumsDao()

    fun refreshData(
        callback: (List<Album>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
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

    fun createAlbum(
        album: Album,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application).crearAlbum(
            album,
            { createdAlbum ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Inserta el álbum creado en Room
                        albumDao.insert(createdAlbum)
                        withContext(Dispatchers.Main) {
                            // Llama al callback con el álbum creado
                            onComplete(createdAlbum)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            onError(VolleyError(e))
                        }
                    }
                }
            },
            onError
        )
    }

    fun associateTrackAlbum(
        idAlbum: String,
        track: Track,
        onComplete: (Track) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application).asociarTrackAlbum(
            idAlbum,
            track,
            { trackAlbum ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Inserta el álbum creado en Room
                        //albumDao.insert(trackAlbum)
                        withContext(Dispatchers.Main) {
                            // Llama al callback con el álbum creado
                            onComplete(trackAlbum)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            onError(VolleyError(e))
                        }
                    }
                }
            },
            onError
        )
    }
}
