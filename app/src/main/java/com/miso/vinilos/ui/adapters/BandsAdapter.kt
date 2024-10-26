package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ItemBandBinding
import com.miso.vinilos.models.Band

class BandsAdapter : RecyclerView.Adapter<BandsAdapter.BandsViewHolder>() {

    var bands: List<Band> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandsViewHolder {
        val withDataBinding: ItemBandBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BandsViewHolder.LAYOUT,
            parent,
            false)
        return BandsViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return bands.size
    }

    override fun onBindViewHolder(holder: BandsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.band = bands[position]
        }
    }

    class BandsViewHolder(val viewDataBinding: ItemBandBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_band
        }
    }
}