package com.capstone.goloak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ItemListTrashBinding
import com.capstone.goloak.model.ListTrash

class HomeAdapter(private val listTrash: ArrayList<ListTrash>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemListTrashBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListTrashBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, title, description, photoUrl, totalPoint) = listTrash[position]
        holder.binding.apply {

        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}