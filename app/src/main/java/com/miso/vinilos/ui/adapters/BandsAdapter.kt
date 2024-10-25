package com.miso.vinilos.ui.adapters

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.models.Band

class BandsAdapter : RecyclerView.Adapter<BandsAdapter.BandsViewHolder>() {

    var bands: List<Band> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandsViewHolder {
        val withDataBinding:
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BandsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    class BandsViewHolder(private val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
                companion object {

                }
            }
}