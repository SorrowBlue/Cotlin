package com.sorrowblue.cotlin.ui.bindingadapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import coil.api.load
import coil.transform.Transformation
import com.sorrowblue.cotlin.ui.R

@BindingAdapter("coil_uri", "coil_transformation", requireAll = false)
fun ImageView.setPhotoUri(uri: Uri? = null, transformation: Transformation? = null) {
	load(uri) {
		allowHardware(false)
		lifecycle(findViewTreeLifecycleOwner())
		placeholder(R.drawable.ic_launcher_foreground)
		transformation?.also { transformations(it) }
	}
}