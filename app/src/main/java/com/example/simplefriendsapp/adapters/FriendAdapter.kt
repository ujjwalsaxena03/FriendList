package com.example.simplefriendsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplefriendsapp.databinding.FriendItemBinding
import com.example.simplefriendsapp.datamodels.FriendListItem

class FriendAdapter(private val onItemClick: (FriendListItem) -> Unit) :
    ListAdapter<FriendListItem, FriendAdapter.FriendItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {
        val binding =
            FriendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FriendItemViewHolder(private val binding: FriendItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    onItemClick(item)
                }
            }
        }

        fun bind(item: FriendListItem) {
            binding.apply {
                tvName.text = item.name
                tvCity.text = item.city
            }
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<FriendListItem>() {
    override fun areItemsTheSame(oldItem: FriendListItem, newItem: FriendListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FriendListItem, newItem: FriendListItem): Boolean {
        return oldItem.name == newItem.name
    }
}