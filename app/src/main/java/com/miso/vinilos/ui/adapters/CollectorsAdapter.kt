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
import com.miso.vinilos.databinding.ItemCollectorBinding
import com.miso.vinilos.models.Collector

class CollectorsAdapter (private val onCollectorClick: (Collector) -> Unit) : ListAdapter<Collector, CollectorsAdapter.CollectorsViewHolder>(CollectorsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorsViewHolder {
        val withDataBinding: ItemCollectorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorsViewHolder.LAYOUT,
            parent,
            false)
        return CollectorsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorsViewHolder, position: Int) {
        val collector = getItem(position)
        holder.viewDataBinding.also {
            it.collector = collector
            val imageUrl = collector.favoritePerformers[0].image
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

    class CollectorsDiffCallback: DiffUtil.ItemCallback<Collector>(){
        override fun areItemsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Collector, newItem: Collector): Boolean {
           return oldItem == newItem
        }

    }
}