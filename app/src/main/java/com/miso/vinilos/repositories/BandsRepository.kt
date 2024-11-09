package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.models.Band
import com.miso.vinilos.network.NetworkServiceAdapter

class BandsRepository(private val application: Application) {

    private var cachedBands: List<Band>? = null

    fun refreshData(callback: (List<Band>) -> Unit, onError: (VolleyError) -> Unit) {
        cachedBands?.let {
            callback(it)
            return
        }
        NetworkServiceAdapter.getInstance(application).getBands(
            {
                cachedBands = it
                callback(it)
            },
            onError
        )
    }
}