package com.capstone.goloak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ItemListTrashBinding
import com.capstone.goloak.helper.withNumberingFormat
import com.capstone.goloak.model.HomeListTrash

class HomeAdapter : ListAdapter<HomeListTrash, HomeAdapter.ListViewHolder>(HomeDiffCallback()) {

    private class HomeDiffCallback : DiffUtil.ItemCallback<HomeListTrash>() {
        override fun areItemsTheSame(oldItem: HomeListTrash, newItem: HomeListTrash): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeListTrash, newItem: HomeListTrash): Boolean {
            return oldItem == newItem
        }
    }

    class ListViewHolder(private val binding: ItemListTrashBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeListTrash) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photo)
                titleTrash.text = data.name
                descTrash.text = data.description
                countRewardPoint.text = itemView.context.getString(R.string.point_reward_count, data.price.toString().withNumberingFormat())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListTrashBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }
}