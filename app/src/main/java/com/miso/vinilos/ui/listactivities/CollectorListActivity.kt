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
import com.miso.vinilos.databinding.ActivityCollectorsListBinding
import com.miso.vinilos.ui.adapters.CollectorsAdapter
import com.miso.vinilos.ui.fragments.CollectorDetailFragment
import com.miso.vinilos.viewmodels.CollectorViewModel

class CollectorListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectorsListBinding
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelAdapter = CollectorsAdapter { collector ->
            val bundle = Bundle().apply {
                putParcelable("COLLECTOR_DETAILS", collector)
            }
            val collectorDetailFragment = CollectorDetailFragment().apply {
                arguments = bundle
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, collectorDetailFragment)
                .commit()
            binding.fragmentContainer.visibility = View.VISIBLE
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CollectorListActivity)
            adapter = viewModelAdapter
        }

        binding.root.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(application))[CollectorViewModel::class.java]

        viewModel.collectors.observe(this, Observer { collectors ->
            collectors?.let {
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