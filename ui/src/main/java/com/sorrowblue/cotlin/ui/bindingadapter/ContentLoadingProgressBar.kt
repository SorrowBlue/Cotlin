package com.sorrowblue.cotlin.ui.bindingadapter

import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("isShown")
fun ContentLoadingProgressBar.isShown(isShown: Boolean?) = if (isShown == true) show() else hide()
