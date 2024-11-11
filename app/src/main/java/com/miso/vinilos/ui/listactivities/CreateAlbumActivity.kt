package com.miso.vinilos.ui.listactivities

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ActivityAlbumsListBinding
import com.miso.vinilos.databinding.ActivityCreateAlbumBinding
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.fragments.AlbumDetailFragment
import com.miso.vinilos.viewmodels.AlbumViewModel

class CreateAlbumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAlbumBinding
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el DatePickerDialog al hacer clic en el campo de fecha
        binding.fragmentAlbumForm.etFechaLanzamiento.setOnClickListener {
            // Obtener la fecha actual
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Crear y mostrar el DatePickerDialog
            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Formatear la fecha seleccionada
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                // Mostrar la fecha en el campo de texto
                binding.fragmentAlbumForm.etFechaLanzamiento.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        binding.root.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(application)).get(AlbumViewModel::class.java)

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

        // Configuración del botón para crear el álbum
        binding.fragmentAlbumForm.btnUsuario2.setOnClickListener {
            var hasError = false
            // Llamar a createAlbum con los datos que se capturan de la UI
            val name = binding.fragmentAlbumForm.etNombre.text.toString()
            if (name.isEmpty()) {
                binding.fragmentAlbumForm.tilNombre.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilNombre.error = null // Limpiar el error si está correcto
            }

            val cover = binding.fragmentAlbumForm.etCaratula.text.toString()
            if (cover.isEmpty()) {
                binding.fragmentAlbumForm.tilCaratula.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilCaratula.error = null
            }
            val releaseDate = binding.fragmentAlbumForm.etFechaLanzamiento.text.toString()
            if (releaseDate.isEmpty()) {
                binding.fragmentAlbumForm.tilFechaLanzamiento.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilFechaLanzamiento.error = null
            }

            val description = binding.fragmentAlbumForm.etDescripcion.text.toString()
            if (description.isEmpty()) {
                binding.fragmentAlbumForm.tilDescripcion.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilDescripcion.error = null
            }

            val genre = binding.fragmentAlbumForm.etGenero.text.toString()
            if (genre.isEmpty()) {
                binding.fragmentAlbumForm.tilGenero.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilGenero.error = null
            }

            val recordLabel = binding.fragmentAlbumForm.etEtiqueta.text.toString()
            if (recordLabel.isEmpty()) {
                binding.fragmentAlbumForm.tilEtiqueta.error = "Este campo es obligatorio"
                hasError = true
            } else {
                binding.fragmentAlbumForm.tilEtiqueta.error = null
            }

            if (!hasError) {
                viewModel.createAlbum(
                    name = name,
                    cover = cover,
                    releaseDate = releaseDate,
                    description = description,
                    genre = genre,
                    recordLabel = recordLabel
                )
            }
        }

    }

    private fun clearInputFields() {
        binding.fragmentAlbumForm.etNombre.text?.clear()
        binding.fragmentAlbumForm.etCaratula.text?.clear()
        binding.fragmentAlbumForm.etFechaLanzamiento.text?.clear()
        binding.fragmentAlbumForm.etDescripcion.text?.clear()
        binding.fragmentAlbumForm.etGenero.text?.clear()
        binding.fragmentAlbumForm.etEtiqueta.text?.clear()
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