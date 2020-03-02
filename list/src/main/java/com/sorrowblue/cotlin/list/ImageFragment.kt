package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.sorrowblue.cotlin.list.databinding.ImageFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.toolbar

internal class ImageFragment :
	DataBindingFragment<ImageFragmentMainBinding>(R.layout.image_fragment_main) {

	private lateinit var adapter: ImagePagerAdapter
	private val args: ImageFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sharedElementEnterTransition = MaterialContainerTransform(requireContext())
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()
//        ViewCompat.setTransitionName(binding.viewPager2, "imageView_${args.position}")
		adapter = ImagePagerAdapter().apply {
			setList(args.images.asList())
		}
		binding.viewPager2.adapter = adapter
		Log.d(javaClass.simpleName, "position ${args.position}")
		binding.viewPager2.setCurrentItem(args.position, false)
		binding.viewPager2.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
		binding.edit.setOnClickListener {
			adapter.setRotation(binding.viewPager2.currentItem, -90)
		}
	}

	override fun onStart() {
		super.onStart()
		toolbar.title = args.images[args.position].name
	}
}
