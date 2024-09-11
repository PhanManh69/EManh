package com.emanh.emanh.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ViewholderItemPostBinding
import com.emanh.emanh.model.PostModel
import com.emanh.emanh.viewModel.HomeViewModel

class ItemPostAdapter(
    private val items: MutableList<PostModel>,
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<ItemPostAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderItemPostBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items[position]
        holder.binding.post = post
        holder.binding.homeViewModel = viewModel
        holder.binding.lifecycleOwner = holder.itemView.context as LifecycleOwner

        Glide.with(holder.itemView.context)
            .load(post.avatar)
            .into(holder.binding.imageViewAvatar)

        holder.binding.textViewFullName.text = post.fullName
        holder.binding.textViewUsername.text = "@${post.username}"
        holder.binding.textViewPost.text = post.post

        val postPhotoAdapter = PostPhotoAdapter(post.postPhoto)
        holder.binding.viewPagerImagePost.adapter = postPhotoAdapter
        holder.binding.indicator.setViewPager(holder.binding.viewPagerImagePost)

        togglePostExpansion(holder)
        toggleLike(holder, post.id)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<PostModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    private fun togglePostExpansion(holder: ViewHolder) {
        val textViewPost = holder.binding.textViewPost
        val textViewSeeMore = holder.binding.textViewSeeMore

        textViewPost.post {
            if (textViewPost.layout.getEllipsisCount(textViewPost.layout.lineCount - 1) > 0) {
                textViewSeeMore.visibility = View.VISIBLE
            } else {
                textViewSeeMore.visibility = View.GONE
            }
        }

        textViewSeeMore.setOnClickListener {
            if (textViewPost.maxLines == 3) {
                textViewPost.maxLines = Int.MAX_VALUE
                textViewSeeMore.visibility = View.GONE
            }
        }
        textViewPost.setOnClickListener {
            if (textViewPost.maxLines == 3) {
                textViewPost.maxLines = Int.MAX_VALUE
                textViewSeeMore.visibility = View.GONE
            } else {
                textViewPost.maxLines = 3
                textViewSeeMore.text = "Xem thêm"
                textViewSeeMore.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun toggleLike(holder: ViewHolder, id: Int) {
        viewModel.likedPosts.observe(holder.binding.lifecycleOwner!!) {
            val isLiked = it[id] ?: false
            val textViewLike = holder.binding.textViewLike

            if (isLiked) {
                textViewLike.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                textViewLike.compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
            } else {
                textViewLike.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                textViewLike.compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.white))
            }
        }

        holder.binding.textViewLike.setOnClickListener {
            viewModel.toggleLike(id)
        }
    }
}