package com.miso.vinilos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.miso.vinilos.R
import com.miso.vinilos.databinding.FragmentBandDetailBinding
import com.miso.vinilos.models.Band

class BandDetailFragment : Fragment() {
    private var _binding: FragmentBandDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBandDetailBinding.inflate(inflater, container, false)

        val band = arguments?.getParcelable<Band>("BAND_DETAILS")

        band?.let {
            binding.bandDetailName.text = it.name
            binding.bandDetailCreationDate.text = it.getFormattedCreationDate()
            binding.bandDetailDescription.text = it.description

            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.bandDetailImage)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}