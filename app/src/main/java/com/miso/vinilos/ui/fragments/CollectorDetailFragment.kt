package com.miso.vinilos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.miso.vinilos.R
import com.miso.vinilos.databinding.FragmentCollectorDetailBinding
import com.miso.vinilos.models.Collector

class CollectorDetailFragment : Fragment() {

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)

        val collector = arguments?.getParcelable<Collector>("COLLECTOR_DETAILS")

        collector?.let {
            binding.collectorDetailName.text = it.favoritePerformers[0].name
            binding.collectorDetailBirthDate.text = it.favoritePerformers[0].getFormattedBirthDate()
            binding.collectorDetailDescription.text = it.favoritePerformers[0].description

            Glide.with(this)
                .load(it.favoritePerformers[0].image)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.collectorAvatar)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}