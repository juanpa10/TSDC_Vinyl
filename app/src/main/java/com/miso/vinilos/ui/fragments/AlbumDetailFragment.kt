package com.miso.vinilos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.miso.vinilos.R
import com.miso.vinilos.databinding.FragmentAlbumDetailBinding
import com.miso.vinilos.models.Album

class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)

        val album = arguments?.getParcelable<Album>("ALBUM_DETAILS")

        album?.let {
            binding.albumDetailName.text = it.name
            binding.albumDetailReleaseDate.text = it.getFormattedReleaseDate()
            binding.albumDetailDescription.text = it.description

            Glide.with(this)
                .load(it.cover)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.albumDetailCover)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}