package com.miso.vinilos.ui.listactivities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ActivityAlbumsListBinding
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.fragments.AlbumDetailFragment
import com.miso.vinilos.viewmodels.AlbumViewModel

class AlbumListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumsListBinding
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelAdapter = AlbumsAdapter { album ->
            val bundle = Bundle().apply {
                putParcelable("ALBUM_DETAILS", album)
            }
            val albumDetailFragment = AlbumDetailFragment().apply {
                arguments = bundle
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, albumDetailFragment)
                .commit()
            binding.fragmentContainer.visibility = View.VISIBLE
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AlbumListActivity)
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

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                if (fragment is AlbumDetailFragment) {
                    supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                    binding.fragmentContainer.visibility = View.GONE
                } else {
                    if (isEnabled) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Error de conexion. Por favor intente de nuevo!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
