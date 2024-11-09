package com.miso.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Band
import com.miso.vinilos.models.Collector
import com.miso.vinilos.models.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter private constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://back-vinilos-81cbe347fbba.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkServiceAdapter(context).also { instance = it }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun cancelRequests(tag: String) {
        requestQueue.cancelAll(tag)
    }

    // Optimización en getAlbums usando función de parseo y retry policy
    fun getAlbums(
        onComplete: (resp: List<Album>) -> Unit,
        onError: (error: VolleyError) -> Unit,
        tag: String = "getAlbums"
    ) {
        requestQueue.add(getRequest("albums",
            { response ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val list = parseAlbums(JSONArray(response))
                        withContext(Dispatchers.Main) { onComplete(list) }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("NetworkServiceAdapter", "Error parsing albums: ${e.message}")
                            onError(VolleyError(e))
                        }
                    }
                }
            },
            { error ->
                Log.e("NetworkServiceAdapter", "Error fetching albums: ${error.message}")
                onError(error)
            }
        ).apply { this.tag = tag })
    }

    // Optimización en getBands usando función de parseo y retry policy
    fun getBands(
        onComplete: (resp: List<Band>) -> Unit,
        onError: (error: VolleyError) -> Unit,
        tag: String = "getBands"
    ) {
        requestQueue.add(getRequest("bands",
            { response ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val list = parseBands(JSONArray(response))
                        withContext(Dispatchers.Main) { onComplete(list) }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("NetworkServiceAdapter", "Error parsing bands: ${e.message}")
                            onError(VolleyError(e))
                        }
                    }
                }
            },
            { error ->
                Log.e("NetworkServiceAdapter", "Error fetching bands: ${error.message}")
                onError(error)
            }
        ).apply { this.tag = tag })
    }

    fun getCollectors(
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit,
        tag: String = "getCollectors"
    ) {
        requestQueue.add(getRequest("collectors",
            { response ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val collectors = parseCollectors(JSONArray(response))
                        withContext(Dispatchers.Main) { onComplete(collectors) }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("NetworkServiceAdapter", "Error parsing collectors: ${e.message}")
                            onError(VolleyError(e))
                        }
                    }
                }
            },
            { error ->
                Log.e("NetworkServiceAdapter", "Error fetching collectors: ${error.message}")
                onError(error)
            }
        ).apply { this.tag = tag })
    }

    // Función auxiliar para crear peticiones GET con caché y retry policy
    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        val request = StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
        request.setShouldCache(true) // Habilita el caché para optimizar el rendimiento
        request.retryPolicy = DefaultRetryPolicy(
            5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Máximo de reintentos
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Factor de retroceso
        )
        return request
    }

    // Función auxiliar para parsear JSON de álbumes
    private fun parseAlbums(jsonArray: JSONArray): List<Album> {
        val list = mutableListOf<Album>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            list.add(
                Album(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    releaseDate = item.getString("releaseDate"),
                    description = item.getString("description"),
                    genre = item.getString("genre"),
                    recordLabel = item.getString("recordLabel")
                )
            )
        }
        return list
    }

    // Función auxiliar para parsear JSON de bandas
    private fun parseBands(jsonArray: JSONArray): List<Band> {
        val list = mutableListOf<Band>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            list.add(
                Band(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    creationDate = item.getString("creationDate")
                )
            )
        }
        return list
    }

    // Función auxiliar para parsear JSON de coleccionistas
    private fun parseCollectors(jsonArray: JSONArray): List<Collector> {
        val list = mutableListOf<Collector>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            list.add(
                Collector(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    telephone = item.getString("telephone"),
                    email = item.getString("email"),
                    comments = parseComments(item.getJSONArray("comments")),
                    favoritePerformers = parseArtists(item.getJSONArray("favoritePerformers"))
                )
            )
        }
        return list
    }

    // Función auxiliar para parsear JSON de comentarios
    private fun parseComments(jsonArray: JSONArray): List<Comment> {
        val list = mutableListOf<Comment>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            list.add(
                Comment(
                    id = item.getInt("id"),
                    description = item.getString("description"),
                    rating = item.getInt("rating")
                )
            )
        }
        return list
    }

    // Función auxiliar para parsear JSON de artistas
    private fun parseArtists(jsonArray: JSONArray): List<Artist> {
        val list = mutableListOf<Artist>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            list.add(
                Artist(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    birthDate = if (item.has("birthDate")) item.getString("birthDate") else item.getString("creationDate")
                )
            )
        }
        return list
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.POST, BASE_URL + path, body, responseListener, errorListener)
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.PUT, BASE_URL + path, body, responseListener, errorListener)
    }
}