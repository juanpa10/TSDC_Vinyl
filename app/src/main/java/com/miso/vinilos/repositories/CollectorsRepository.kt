package com.miso.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso.vinilos.database.VinylRoomDatabase
import com.miso.vinilos.models.Collector
import com.miso.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorsRepository(private val application: Application) {

    private val collectorsDao = VinylRoomDatabase.getDatabase(application).collectorsDao()

    fun refreshData(callback: (List<Collector>) -> Unit, onError: (VolleyError) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cachedCollectors = collectorsDao.getCollectors()
                if (cachedCollectors.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        callback(cachedCollectors)
                    }
                } else {
                    fetchCollectorsFromNetwork(callback, onError)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(VolleyError(e))
                }
            }
        }
    }

    private fun fetchCollectorsFromNetwork(callback: (List<Collector>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors(
            { collectors ->
                CoroutineScope(Dispatchers.IO).launch {
                    collectorsDao.deleteAll()
                    collectors.forEach { collector ->
                        collectorsDao.insert(collector)
                    }
                    withContext(Dispatchers.Main) {
                        callback(collectors)
                    }
                }
            },
            onError
        )
    }
}