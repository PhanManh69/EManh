@file:Suppress("DEPRECATION")

package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityCommentPostBinding
import com.emanh.emanh.model.PostModel
import com.emanh.emanh.view.adapter.PostPhotoAdapter
import com.emanh.emanh.viewModel.HomeViewModel

class CommentPostActivity : BaseActivity() {

    private lateinit var binding: ActivityCommentPostBinding
    private lateinit var post: PostModel
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        post = intent.getParcelableExtra("objectPost")!!

        Glide.with(this)
            .load(post.avatar)
            .into(binding.imageViewAvatar)

        binding.textViewFullName.text = post.fullName
        binding.textViewUsername.text = "@${post.username}"
        binding.textViewPost.text = post.post

        val postPhotoAdapter = PostPhotoAdapter(post.postPhoto, homeViewModel)
        binding.viewPagerImagePost.adapter = postPhotoAdapter
        binding.indicator.setViewPager(binding.viewPagerImagePost)

        togglePostExpansion()
        toggleLike(post.id)
    }

    @SuppressLint("SetTextI18n")
    private fun togglePostExpansion() {
        val textViewPost = binding.textViewPost
        val textViewSeeMore = binding.textViewSeeMore

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

    @SuppressLint("UseCompatTextViewDrawableApis", "SetTextI18n")
    private fun toggleLike(id: Int) {
        homeViewModel.likedPosts.observe(this) {
            val isLiked = it[id] ?: false
            val textViewNumber = binding.textViewNumberLike
            val textViewLike = binding.textViewLike
            var numberLike = textViewNumber.text.toString().toIntOrNull() ?: 0

            if (isLiked) {
                numberLike += 1
                textViewNumber.setTextColor(ContextCompat.getColor(this, R.color.red))
                textViewNumber.text = numberLike.toString()
                textViewLike.setTextColor(ContextCompat.getColor(this, R.color.red))
                textViewLike.compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                if (numberLike > 0) numberLike -= 1
                textViewNumber.setTextColor(ContextCompat.getColor(this, R.color.white))
                textViewNumber.text = numberLike.toString()
                textViewLike.setTextColor(ContextCompat.getColor(this, R.color.white))
                textViewLike.compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            }

            textViewNumber.text = numberLike.toString()
        }

        binding.textViewLike.setOnClickListener {
            homeViewModel.toggleLike(id)
        }
    }
}