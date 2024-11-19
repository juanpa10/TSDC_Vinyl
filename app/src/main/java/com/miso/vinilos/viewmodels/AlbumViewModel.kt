package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.vinilos.models.Album
import com.miso.vinilos.repositories.AlbumsRepository

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumsRepository = AlbumsRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    private val _albumCreationStatus = MutableLiveData<Boolean>()
    val albumCreationStatus: LiveData<Boolean>
        get() = _albumCreationStatus

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        loadAlbumsIfNeeded()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
        onErrorHandled()
    }

    private fun loadAlbumsIfNeeded() {
        if (_albums.value.isNullOrEmpty()) {
            refreshDataFromNetwork()
        }
    }

    private fun refreshDataFromNetwork() {
        albumsRepository.refreshData(
            {
                if (_albums.value != it) {
                    _albums.value = it
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                }
            },
            {
                _eventNetworkError.value = true
            }
        )
    }

    // Método para crear un nuevo álbum desde la UI
    fun createAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String
    ) {
        val newAlbum = Album(
            id = 0, // ID inicializado en 0 o con constructor alternativo si es necesario
            name = name,
            cover = cover,
            releaseDate = releaseDate,
            description = description,
            genre = genre,
            recordLabel = recordLabel
        )

        albumsRepository.createAlbum(newAlbum,
            onComplete = { createdAlbum ->
                // Actualizar la lista de álbumes en el ViewModel
                _albums.value = _albums.value?.toMutableList()?.apply { add(createdAlbum) }
                _albumCreationStatus.value = true
            },
            onError = {
                _albumCreationStatus.value = false
            }
        )
    }

    private fun onErrorHandled() {
        _eventNetworkError.value = false
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}