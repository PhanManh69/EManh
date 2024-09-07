package com.emanh.emanh.model

import com.emanh.emanh.databinding.DesignButtonBarItemBinding

data class ButtonBarItemModel(
    val binding: DesignButtonBarItemBinding,
    val iconRes: Int,
    val text: String,
    val position: Int
)
