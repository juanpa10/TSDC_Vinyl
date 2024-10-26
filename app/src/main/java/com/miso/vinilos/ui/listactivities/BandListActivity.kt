package com.miso.vinilos.ui.listactivities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ActivityArtistsListBinding
import com.miso.vinilos.ui.adapters.BandsAdapter
import com.miso.vinilos.viewmodels.BandViewModel

class BandListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtistsListBinding
    private lateinit var viewModel: BandViewModel
    private var viewModelAdapter: BandsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración inicial del adaptador
        viewModelAdapter = BandsAdapter()

        // Configuración del RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BandListActivity)
            adapter = viewModelAdapter
        }

        // Inicialización del ViewModel
        viewModel = ViewModelProvider(this, BandViewModel.Factory(application)).get(BandViewModel::class.java)

        // Observadores de los datos y errores de red
        viewModel.bands.observe(this, Observer { bands ->
            bands?.let {
                viewModelAdapter?.bands = it
            }
        })

        viewModel.eventNetworkError.observe(this, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error. Please try again!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}