package com.emanh.emanh.view.bindingAdapter

import android.annotation.SuppressLint
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("android:drawableStart")
fun setDrawableStart(view: AutoCompleteTextView, drawableRes: Int?) {
    val drawables = view.compoundDrawables
    val drawableStart = drawableRes?.let { view.context.getDrawable(it) }

    view.setCompoundDrawablesWithIntrinsicBounds(
        drawableStart,
        drawables[1],
        drawables[2],
        drawables[3]
    )
}
