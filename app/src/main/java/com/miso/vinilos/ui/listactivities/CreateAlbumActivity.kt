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

        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(application)
        ).get(AlbumViewModel::class.java)

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
            val fields = listOf(
                binding.fragmentAlbumForm.etNombre to binding.fragmentAlbumForm.tilNombre,
                binding.fragmentAlbumForm.etCaratula to binding.fragmentAlbumForm.tilCaratula,
                binding.fragmentAlbumForm.etFechaLanzamiento to binding.fragmentAlbumForm.tilFechaLanzamiento,
                binding.fragmentAlbumForm.etDescripcion to binding.fragmentAlbumForm.tilDescripcion,
                binding.fragmentAlbumForm.etGenero to binding.fragmentAlbumForm.tilGenero,
                binding.fragmentAlbumForm.etEtiqueta to binding.fragmentAlbumForm.tilEtiqueta
            )

            var hasError = false

            fields.forEach { (editText, textInputLayout) ->
                if (editText.text.toString().isEmpty()) {
                    textInputLayout.error = "Este campo es obligatorio"
                    hasError = true
                } else {
                    textInputLayout.error = null
                }
            }

            if (!hasError) {
                viewModel.createAlbum(
                    name = binding.fragmentAlbumForm.etNombre.text.toString(),
                    cover = binding.fragmentAlbumForm.etCaratula.text.toString(),
                    releaseDate = binding.fragmentAlbumForm.etFechaLanzamiento.text.toString(),
                    description = binding.fragmentAlbumForm.etDescripcion.text.toString(),
                    genre = binding.fragmentAlbumForm.etGenero.text.toString(),
                    recordLabel = binding.fragmentAlbumForm.etEtiqueta.text.toString()
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