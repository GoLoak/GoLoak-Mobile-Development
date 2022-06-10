package com.capstone.goloak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ItemPointHistoryBinding
import com.capstone.goloak.helper.withDateFormat
import com.capstone.goloak.helper.withNumberingFormat
import com.capstone.goloak.model.ProfileListPointHistory

class PointHistoryAdapter : ListAdapter<ProfileListPointHistory, PointHistoryAdapter.ListViewHolder>(PointHistoryDiffCallback()) {

    private class PointHistoryDiffCallback : DiffUtil.ItemCallback<ProfileListPointHistory>() {
        override fun areItemsTheSame(oldItem: ProfileListPointHistory, newItem: ProfileListPointHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfileListPointHistory, newItem: ProfileListPointHistory): Boolean {
            return oldItem == newItem
        }
    }

    class ListViewHolder(private val binding: ItemPointHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProfileListPointHistory) {
            binding.apply {
                titleTrash.text = data.status
                countRewardPoint.text = itemView.context.getString(R.string.point_minus_count, data.pointUsed.toString().withNumberingFormat())
                date.text = itemView.context.getString(R.string.date, data.startDate.withDateFormat())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }
}