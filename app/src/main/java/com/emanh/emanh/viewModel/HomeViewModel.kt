package com.emanh.emanh.viewModel

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.emanh.emanh.R
import com.emanh.emanh.model.Database
import com.emanh.emanh.model.FriendsModel
import com.emanh.emanh.model.PostModel
import com.emanh.emanh.view.adapter.ItemPostAdapter
import com.emanh.emanh.view.adapter.PostPhotoAdapter
import com.google.android.material.imageview.ShapeableImageView

class HomeViewModel : ViewModel() {
    private val _friendsList = MutableLiveData<MutableList<FriendsModel>>()
    val friendsList: LiveData<MutableList<FriendsModel>> get() = _friendsList

    private val _postList = MutableLiveData<MutableList<PostModel>>()
    val postList: LiveData<MutableList<PostModel>> get() = _postList

    private val _likedPosts = MutableLiveData<MutableMap<Int, Boolean>>()
    val likedPosts: LiveData<MutableMap<Int, Boolean>> get() = _likedPosts

    init {
        val friends = Database().friends()
        _friendsList.value = friends

        val posts = Database().posts()
        _postList.value = posts

        _likedPosts.value = mutableMapOf()
    }

    fun toggleLike(postId: Int) {
        val currentLikes = _likedPosts.value ?: mutableMapOf()
        val isLiked = currentLikes[postId] ?: false
        currentLikes[postId] = !isLiked
        _likedPosts.value = currentLikes
    }

    @SuppressLint("InflateParams")
    fun showPhotoPost(context: Context, holder: PostPhotoAdapter.ViewHolder, imageUrl: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_photo_post, null)
        val dialog = Dialog(holder.itemView.context, R.style.DialogTheme)
        dialog.setContentView(dialogView)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)

        val photoPost = dialogView.findViewById<ShapeableImageView>(R.id.imageViewPhoto)

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(photoPost)

        dialog.show()
    }
}