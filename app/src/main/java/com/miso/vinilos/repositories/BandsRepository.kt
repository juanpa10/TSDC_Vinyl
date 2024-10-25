package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.models.Band
import com.miso.vinilos.network.NetworkServiceAdapter

class BandsRepository (private val application: Application){
    fun refreshData(callback: (List<Band>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getBands({
            callback(it)
        },
            onError
        )
    }
}