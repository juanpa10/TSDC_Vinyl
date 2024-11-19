package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ItemBandBinding
import com.miso.vinilos.models.Band

class BandsAdapter(private val onBandClick: (Band) -> Unit) : ListAdapter<Band,BandsAdapter.BandsViewHolder>(BandsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandsViewHolder {
        val withDataBinding: ItemBandBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BandsViewHolder.LAYOUT,
            parent,
            false)
        return BandsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: BandsViewHolder, position: Int) {
        val band = getItem(position)
        holder.viewDataBinding.also {
            it.band = band
            val imageUrl = band.image
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(it.bandImage)
            it.root.setOnClickListener { onBandClick(band) }
        }
    }

    class BandsViewHolder(val viewDataBinding: ItemBandBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_band
        }
    }

    class BandsDiffCallback : DiffUtil.ItemCallback<Band>(){
        override fun areItemsTheSame(oldItem: Band, newItem: Band): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Band, newItem: Band): Boolean {
            return oldItem == newItem
        }
    }
}