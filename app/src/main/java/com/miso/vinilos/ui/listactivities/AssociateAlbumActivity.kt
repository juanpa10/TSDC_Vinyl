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

        viewModelAdapter = AlbumsAssociateAdapter { album ->
            val etId = binding.trackFragment.etId
            etId.setText(album.id.toString())
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AssociateAlbumActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = viewModelAdapter
        }

        binding.root.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(application)).get(AlbumViewModel::class.java)

        viewModel.albums.observe(this, Observer { albums ->
            albums?.let {
                viewModelAdapter!!.submitList(it)
            }
        })

        viewModel.eventNetworkError.observe(this, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.albumCreationStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(this, "Álbum creado exitosamente", Toast.LENGTH_SHORT).show()
                // Limpiar los campos de entrada en la vista
                clearInputFields()
            } else {
                Toast.makeText(this, "Error al crear el álbum", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.albumTrackStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(this, "Track asociado a Álbum exitosamente", Toast.LENGTH_SHORT).show()
                // Limpiar los campos de entrada en la vista
                clearInputFields()
            } else {
                Toast.makeText(this, "Error al asociar Track al álbum", Toast.LENGTH_SHORT).show()
            }
        })

        // Configuración del botón para crear el álbum
        binding.trackFragment.btnUsuario3.setOnClickListener {
            var hasError = false
            // Llamar a asociarTrack con los datos que se capturan de la UI
            val id = binding.trackFragment.etId.text.toString()
            if (id.isEmpty()) {
                binding.trackFragment.tilId.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.trackFragment.tilId.error = null // Limpiar el error si está correcto
            }

            val name = binding.trackFragment.etNombre.text.toString()
            if (name.isEmpty()) {
                binding.trackFragment.tilNombre.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.trackFragment.tilNombre.error = null // Limpiar el error si está correcto
            }

            val duration = binding.trackFragment.etDuracion.text.toString()
            if (duration.isEmpty()) {
                binding.trackFragment.tilDuracion.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.trackFragment.tilDuracion.error = null
            }

            if (!hasError) {
                viewModel.associateTrackAlbum(
                    id = id,
                    name = name,
                    duration = duration
                )
            }
        }

    }

    private fun clearInputFields() {
        binding.trackFragment.etNombre.text?.clear()
        binding.trackFragment.etId.text?.clear()
        binding.trackFragment.etDuracion.text?.clear()
    }

    // Sobrescribir el comportamiento del back button del sistema
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
            Toast.makeText(this, "Error de conexion. Por favor intente de nuevo!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }else if(!viewModel.eventNetworkError.value!!) {
            Toast.makeText(this, "Error de conexion. Por favor intente de nuevo!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}