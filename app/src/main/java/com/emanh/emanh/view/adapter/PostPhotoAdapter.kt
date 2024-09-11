package com.emanh.emanh.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emanh.emanh.databinding.ViewholderPostPhotoBinding

class PostPhotoAdapter(
    private val items: MutableList<String>
) : RecyclerView.Adapter<PostPhotoAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderPostPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderPostPhotoBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.imageViewPhoto)
    }
}