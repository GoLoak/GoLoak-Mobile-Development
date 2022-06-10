package com.capstone.goloak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ItemHistoryBinding
import com.capstone.goloak.helper.withDateFormat
import com.capstone.goloak.model.HomeListHistory

class HistoryAdapter : ListAdapter<HomeListHistory, HistoryAdapter.ListViewHolder>(HistoryDiffCallback()) {

    private class HistoryDiffCallback : DiffUtil.ItemCallback<HomeListHistory>() {
        override fun areItemsTheSame(oldItem: HomeListHistory, newItem: HomeListHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeListHistory, newItem: HomeListHistory): Boolean {
            return oldItem == newItem
        }
    }

    class ListViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeListHistory) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photo)
                titleHistory.text = data.nameTrash
                statusHistory.text = data.status
                date.text = itemView.context.getString(R.string.date, data.createAt.withDateFormat())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }
}