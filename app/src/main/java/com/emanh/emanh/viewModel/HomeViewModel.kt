package com.emanh.emanh.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.emanh.model.Database
import com.emanh.emanh.model.FriendsModel
import com.emanh.emanh.model.PostModel

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
}