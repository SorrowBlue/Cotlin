package com.sorrowblue.cotlin.ui.bindingadapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import coil.api.load
import coil.transform.BlurTransformation
import com.sorrowblue.cotlin.ui.R

@BindingAdapter("coil_uri")
fun ImageView.setPhotoUri(uri: Uri? = null) {
	load(uri) {
		lifecycle(findViewTreeLifecycleOwner())
		placeholder(R.drawable.ic_launcher_foreground)
		transformations(BlurTransformation(context, 2f, 2f))
	}
}