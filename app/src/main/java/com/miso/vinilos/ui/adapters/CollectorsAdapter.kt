package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ItemCollectorBinding
import com.miso.vinilos.models.Collector

class CollectorsAdapter (private val onCollectorClick: (Collector) -> Unit) : RecyclerView.Adapter<CollectorsAdapter.CollectorsViewHolder>() {

    var collectors: List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorsViewHolder {
        val withDataBinding: ItemCollectorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorsViewHolder.LAYOUT,
            parent,
            false)
        return CollectorsViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    override fun onBindViewHolder(holder: CollectorsViewHolder, position: Int) {
        val collector = collectors[position]
        holder.viewDataBinding.also {
            it.collector = collectors[position]
            val imageUrl = collectors[position].favoritePerformers[0].image
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(it.collectorAvatar)
            it.root.setOnClickListener { onCollectorClick(collector) }
        }
    }

    class CollectorsViewHolder(val viewDataBinding: ItemCollectorBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_collector
        }
    }
}