package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ItemAlbumAsociarBinding
import com.miso.vinilos.models.Album

class AlbumsAssociateAdapter(private val onAlbumClick: (Album) -> Unit) : ListAdapter<Album, AlbumsAssociateAdapter.AlbumsAssociateViewHolder>(AlbumsAssociateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAssociateViewHolder {
        val withDataBinding: ItemAlbumAsociarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumsAssociateViewHolder.LAYOUT,
            parent,
            false)
        return AlbumsAssociateViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumsAssociateViewHolder, position: Int) {
        val album = getItem(position)
        holder.viewDataBinding.also {
            it.album = album
            val imageUrl = album.cover
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(it.albumCover)
            it.root.setOnClickListener { onAlbumClick(album) }
        }
    }

    class AlbumsAssociateViewHolder(val viewDataBinding: ItemAlbumAsociarBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_album_asociar
        }
    }

    class AlbumsAssociateDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}
