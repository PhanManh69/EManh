package com.emanh.emanh.view.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import android.content.res.ColorStateList

@BindingAdapter("app:tint")
fun setTintColor(imageView: ImageView, color: Int) {
    imageView.imageTintList = ColorStateList.valueOf(color)
}