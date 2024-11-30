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
import com.miso.vinilos.databinding.ActivityAsociarTrackBinding
import com.miso.vinilos.ui.adapters.AlbumsAssociateAdapter
import com.miso.vinilos.ui.fragments.AlbumDetailFragment
import com.miso.vinilos.viewmodels.AlbumViewModel

class AssociateAlbumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsociarTrackBinding
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAssociateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsociarTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        viewModelAdapter = AlbumsAssociateAdapter { album ->
            binding.trackFragment.etId.setText(album.id.toString())
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = viewModelAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(application)).get(AlbumViewModel::class.java)

        viewModel.albums.observe(this, Observer { albums ->
            albums?.let { viewModelAdapter?.submitList(it) }
        })

        viewModel.eventNetworkError.observe(this, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.albumCreationStatus.observe(this, Observer { isSuccess ->
            showToast(if (isSuccess == true) "Álbum creado exitosamente" else "Error al crear el álbum")
            if (isSuccess == true) clearInputFields()
        })

        viewModel.albumTrackStatus.observe(this, Observer { isSuccess ->
            showToast(if (isSuccess == true) "Track asociado a Álbum exitosamente" else "Error al asociar Track al álbum")
            if (isSuccess == true) clearInputFields()
        })
    }

    private fun setupListeners() {
        binding.root.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        binding.trackFragment.btnUsuario3.setOnClickListener {
            if (validateInputFields()) {
                viewModel.associateTrackAlbum(
                    id = binding.trackFragment.etId.text.toString(),
                    name = binding.trackFragment.etNombre.text.toString(),
                    duration = binding.trackFragment.etDuracion.text.toString()
                )
            }
        }
    }

    private fun validateInputFields(): Boolean {
        val fields = listOf(
            binding.trackFragment.etId to binding.trackFragment.tilId,
            binding.trackFragment.etNombre to binding.trackFragment.tilNombre,
            binding.trackFragment.etDuracion to binding.trackFragment.tilDuracion
        )

        var hasError = false
        fields.forEach { (editText, inputLayout) ->
            if (editText.text.toString().isEmpty()) {
                inputLayout.error = "Este campo es obligatorio"
                hasError = true
            } else {
                inputLayout.error = null
            }
        }

        return !hasError
    }

    private fun clearInputFields() {
        binding.trackFragment.etNombre.text?.clear()
        binding.trackFragment.etId.text?.clear()
        binding.trackFragment.etDuracion.text?.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is AlbumDetailFragment) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
            binding.fragmentContainer.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            showToast("Error de conexión. Por favor intente de nuevo!")
            viewModel.onNetworkErrorShown()
        } else if (!viewModel.eventNetworkError.value!!) {
            showToast("Error de conexión. Por favor intente de nuevo!")
            viewModel.onNetworkErrorShown()
        }
    }
}