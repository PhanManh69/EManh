package com.emanh.emanh.view.bindingAdapter

import android.annotation.SuppressLint
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("android:drawableEnd")
fun setDrawableEnd(view: AutoCompleteTextView, drawableRes: Int?) {
    val drawables = view.compoundDrawables
    val drawableEnd = drawableRes?.let { view.context.getDrawable(it) }

    view.setCompoundDrawablesWithIntrinsicBounds(
        drawables[0],
        drawables[1],
        drawableEnd,
        drawables[3]
    )
}
