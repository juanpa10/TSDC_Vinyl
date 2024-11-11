package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.models.Album
import com.miso.vinilos.network.NetworkServiceAdapter

class AlbumsRepository(private val application: Application) {

    private var cachedAlbums: List<Album>? = null

    fun refreshData(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        cachedAlbums?.let {
            callback(it)
            return
        }
        NetworkServiceAdapter.getInstance(application).getAlbums(
            {
                cachedAlbums = it
                callback(it)
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
                // Actualiza el caché agregando el nuevo álbum a la lista
                cachedAlbums = cachedAlbums?.toMutableList()?.apply { add(createdAlbum) } ?: listOf(createdAlbum)
                onComplete(createdAlbum)
            },
            onError
        )
    }
}