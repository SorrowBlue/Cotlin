package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.google.android.material.transition.MaterialContainerTransform
import com.sorrowblue.cotlin.list.databinding.ImageFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment

internal class ImageFragment :
	DataBindingFragment<ImageFragmentMainBinding>(R.layout.image_fragment_main) {

	private val args: ImageFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sharedElementEnterTransition = MaterialContainerTransform(requireContext())
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()

		binding.imageView2.load(args.uri)
		ViewCompat.setTransitionName(binding.imageView2, "imageView_${args.position}")
		view.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
	}
}
