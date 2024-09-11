package com.emanh.emanh.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emanh.emanh.databinding.ViewholderItemFriendBinding
import com.emanh.emanh.model.FriendsModel

class ItemFriendAdapter(
    private val items: MutableList<FriendsModel>
) : RecyclerView.Adapter<ItemFriendAdapter.ViewHolder>() {

    private var context: Context? = null
    class ViewHolder(val binding: ViewholderItemFriendBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderItemFriendBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = items[position]

        Glide.with(holder.itemView.context)
            .load(friend.avatar)
            .into(holder.binding.imageViewAvatar)

        holder.binding.textViewUsername.text = friend.username
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<FriendsModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}