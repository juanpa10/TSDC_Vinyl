package com.miso.vinilos.ui.listactivities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ActivityArtistsListBinding
import com.miso.vinilos.ui.adapters.BandsAdapter
import com.miso.vinilos.ui.fragments.BandDetailFragment
import com.miso.vinilos.viewmodels.BandViewModel

class BandListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtistsListBinding
    private lateinit var viewModel: BandViewModel
    private var viewModelAdapter: BandsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelAdapter = BandsAdapter { band ->
            val bundle = Bundle().apply {
                putParcelable("BAND_DETAILS", band)
            }
            val bandDetailFragment = BandDetailFragment().apply {
                arguments = bundle
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bandDetailFragment)
                .addToBackStack(null)
                .commit()
            binding.fragmentContainer.visibility = View.VISIBLE
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BandListActivity)
            adapter = viewModelAdapter
        }

        binding.root.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this, BandViewModel.Factory(application)).get(BandViewModel::class.java)

        viewModel.bands.observe(this, Observer { bands ->
            bands?.let {
                viewModelAdapter!!.submitList(it)
            }
        })

        viewModel.eventNetworkError.observe(this, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Error de conexion. Por favor intente de nuevo!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}