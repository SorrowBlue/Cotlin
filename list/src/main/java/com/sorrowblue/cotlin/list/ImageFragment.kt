package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.transition.TransitionManager
import android.transition.Visibility
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.list.databinding.ImageFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import com.sorrowblue.cotlin.ui.fragment.toolbar
import com.sorrowblue.cotlin.ui.view.applyNavigationBarPaddingInsets

internal class ImageFragment :
	DataBindingFragment<ImageFragmentMainBinding>(R.layout.image_fragment_main) {

	private lateinit var adapter: ImagePagerAdapter
	private val args: ImageFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val forward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, true)
		enterTransition = forward

		val backward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, false)
		exitTransition = backward
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()
		binding.favorite.applyNavigationBarPaddingInsets()
		binding.edit.applyNavigationBarPaddingInsets()
		binding.share.applyNavigationBarPaddingInsets()
		binding.delete.applyNavigationBarPaddingInsets()
		adapter = ImagePagerAdapter().apply {
			setList(args.images.asList())
		}
		binding.viewPager2.adapter = adapter
		binding.viewPager2.setCurrentItem(args.position, false)
		binding.viewPager2.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
		binding.edit.setOnClickListener {
			adapter.setRotation(binding.viewPager2.currentItem, -90)
		}
		adapter.onClick = {
			MaterialFade.create(requireContext()).also {
				TransitionManager.beginDelayedTransition(toolbar.parent as ViewGroup, it)
				toolbar.isVisible = !toolbar.isVisible
				binding.view.isVisible = !binding.view.isVisible
				binding.group.isVisible = !binding.group.isVisible
			}
		}
	}

	override fun onStart() {
		super.onStart()
		toolbar.title = args.images[args.position].name
	}

	override fun onStop() {
		super.onStop()
		val appBar = appBarLayout
		MaterialFade.create(requireContext()).also {
			TransitionManager.beginDelayedTransition(appBar.parent as ViewGroup, it)
			appBar.isVisible = true
		}

	}
}
