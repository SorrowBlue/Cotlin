package com.sorrowblue.cotlin.futures.image

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.domains.image.ImageRepository
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import com.sorrowblue.cotlin.ui.fragment.toolbar
import com.sorrowblue.cotlin.ui.view.applyNavigationBarBottomMarginInsets
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import com.sorrowblue.cotlin.futures.image.databinding.ImageFragmentMainBinding as FragmentBinding

internal class ImageFragment : DataBindingFragment<FragmentBinding>(R.layout.image_fragment_main) {

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
		ViewCompat.setTransitionName(binding.root, args.transitionName)
		binding.root.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
		binding.favorite.applyNavigationBarBottomMarginInsets()
		binding.edit.applyNavigationBarBottomMarginInsets()
		binding.share.applyNavigationBarBottomMarginInsets()
		binding.delete.applyNavigationBarBottomMarginInsets()
		adapter = ImagePagerAdapter().apply {
			setList(args.images.asList())
		}
		binding.favorite.setOnClickListener {
			val checked = !((binding.favorite.tag as? Boolean) ?: false)
			val state = if (checked) intArrayOf(android.R.attr.state_checked) else intArrayOf()
			binding.favorite.setImageState(state, true)
			binding.favorite.tag = checked
		}
		binding.viewPager2.adapter = adapter
		binding.viewPager2.setCurrentItem(args.position, false)
		binding.edit.setOnClickListener {
			adapter.setRotation(binding.viewPager2.currentItem, -90)
		}
		adapter.onClick = {
			MaterialFade.create(requireContext()).also {
				TransitionManager.beginDelayedTransition(toolbar.parent as ViewGroup, it)
				appBarLayout.isVisible = !appBarLayout.isVisible
				binding.group.isVisible = !binding.group.isVisible
			}
		}
		binding.share.setOnClickListener {
			val editIntent = Intent(Intent.ACTION_SEND)
			editIntent.setDataAndType(
				adapter.currentList[binding.viewPager2.currentItem].uri,
				adapter.currentList[binding.viewPager2.currentItem].contentType
			)
			editIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			startActivity(Intent.createChooser(editIntent, "Share images..."))
		}
		binding.delete.setOnClickListener {
			viewLifecycleOwner.lifecycle.coroutineScope.launch {
				get<ImageRepository>().delete(
					adapter.currentList[binding.viewPager2.currentItem]
				) {
					startIntentSenderForResult(it, 100, null, 0, 0, 0, null)
				}
			}
		}
		binding.edit.setOnClickListener {
			val editIntent = Intent(Intent.ACTION_EDIT)
			editIntent.setDataAndType(
				adapter.currentList[binding.viewPager2.currentItem].uri,
				adapter.currentList[binding.viewPager2.currentItem].contentType
			)
			editIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			startActivity(editIntent)
		}
		postponeEnterTransition()
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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		Log.d(
			javaClass.simpleName, """
			requestCode == $requestCode
					${data?.extras?.keySet()?.toList()}
				""".trimIndent()
		)
		if (resultCode == Activity.RESULT_OK && requestCode == 100) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				requireContext().contentResolver.delete(
					adapter.currentList[binding.viewPager2.currentItem].uri,
					null,
					null
				)
				Log.d(
					javaClass.simpleName, """
					${data?.extras?.keySet()?.toList()}
				""".trimIndent()
				)
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data)
		}
	}
}
