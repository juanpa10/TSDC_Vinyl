package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.database.VinylRoomDatabase
import com.miso.vinilos.models.Band
import com.miso.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BandsRepository(private val application: Application) {

    private val bandsDao = VinylRoomDatabase.getDatabase(application).bandsDao()

    fun refreshData(callback: (List<Band>) -> Unit, onError: (VolleyError) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cachedBands = bandsDao.getBands()
                if (cachedBands.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        callback(cachedBands)
                    }
                } else {
                    fetchBandsFromNetwork(callback, onError)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(VolleyError(e))
                }
            }
        }
    }

    private fun fetchBandsFromNetwork(callback: (List<Band>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getBands(
            { bands ->
                CoroutineScope(Dispatchers.IO).launch {
                    bandsDao.deleteAll()
                    bands.forEach { band ->
                        bandsDao.insert(band)
                    }
                    withContext(Dispatchers.Main) {
                        callback(bands)
                    }
                }
            },
            onError
        )
    }
}