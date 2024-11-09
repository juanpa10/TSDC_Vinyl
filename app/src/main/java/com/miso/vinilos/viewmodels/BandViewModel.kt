package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.vinilos.models.Band
import com.miso.vinilos.repositories.BandsRepository

class BandViewModel(application: Application) : AndroidViewModel(application) {

    private val bandsRepository = BandsRepository(application)
    private val _bands = MutableLiveData<List<Band>>()

    val bands: LiveData<List<Band>>
        get() = _bands

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        loadBandsIfNeeded()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
        onErrorHandled()
    }

    private fun loadBandsIfNeeded() {
        if (_bands.value.isNullOrEmpty()) {
            refreshDataFromNetwork()
        }
    }

    private fun refreshDataFromNetwork() {
        bandsRepository.refreshData(
            {
                if (_bands.value != it) {
                    _bands.value = it
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
            if (modelClass.isAssignableFrom(BandViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BandViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}