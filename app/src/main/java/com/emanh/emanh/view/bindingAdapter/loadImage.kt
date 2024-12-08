package com.emanh.emanh.view.bindingAdapter

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imageUri")
fun loadImage(view: ShapeableImageView, uri: Uri?) {
    uri?.let {
        view.setImageURI(it)
    }
}