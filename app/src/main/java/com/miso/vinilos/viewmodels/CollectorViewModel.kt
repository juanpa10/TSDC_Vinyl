package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.vinilos.models.Collector
import com.miso.vinilos.repositories.CollectorsRepository

class CollectorViewModel (application: Application) : AndroidViewModel(application) {

    private val collectorRepository = CollectorsRepository(application)
    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        loadCollectorsIfNeeded()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
        onErrorHandled()
    }

    private fun loadCollectorsIfNeeded() {
        if (_collectors.value.isNullOrEmpty()) {
            refreshDataFromNetwork()
        }
    }

    private fun refreshDataFromNetwork() {
        collectorRepository.refreshData(
            {
                if (_collectors.value != it) {
                    _collectors.value = it
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                }
            },
            {
                _eventNetworkError.value = true
            }
        )
    }

    private fun onErrorHandled() {
        _eventNetworkError.value = false
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}