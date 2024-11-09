package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.models.Collector
import com.miso.vinilos.network.NetworkServiceAdapter

class CollectorsRepository (private val application: Application) {

    private var cachedCollectors: List<Collector>? = null

    fun refreshData(callback: (List<Collector>) -> Unit, onError: (VolleyError) -> Unit) {
        cachedCollectors?.let {
            callback(it)
            return
        }
        NetworkServiceAdapter.getInstance(application).getCollectors(
            {
                cachedCollectors = it
                callback(it)
            },
            onError
        )
    }
}