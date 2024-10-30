package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.models.Album
import com.miso.vinilos.network.NetworkServiceAdapter

class AlbumsRepository (private val application: Application) {

    fun refreshData(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }
}