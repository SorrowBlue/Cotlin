package com.sorrowblue.cotlin.futures.image

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.domains.image.ImageRepository
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import com.sorrowblue.cotlin.ui.fragment.toolbar
import com.sorrowblue.cotlin.ui.view.applyNavigationBarBottomMarginInsets
import com.sorrowblue.cotlin.ui.view.item
import com.sorrowblue.cotlin.ui.view.setOnClickListener
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import com.sorrowblue.cotlin.futures.image.databinding.ImageFragmentMainBinding as FragmentBinding

internal class ImageFragment :
	DataBindingFragment<FragmentBinding>(R.layout.image_fragment_main, R.menu.image_main) {

	private val args: ImageFragmentArgs by navArgs()
	private val viewModel: ImageViewModel by viewModel { parametersOf(args.folder) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enterTransition = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, true)
		exitTransition = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.viewModel = viewModel
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
		viewModel.action.observe(this) {
			when (it) {
				ImageViewModel.Action.FAVORITE -> favorite()
				ImageViewModel.Action.EDIT -> startEdit()
				ImageViewModel.Action.SHARE -> startShare()
				ImageViewModel.Action.OPEN -> openOther()
				ImageViewModel.Action.DELETE -> startDelete()
			}
		}
		binding.favorite.applyNavigationBarBottomMarginInsets()
		binding.count.applyNavigationBarBottomMarginInsets()
		viewModel.adapter.onClick = {
			MaterialFade.create(requireContext(), true).also {
				TransitionManager.beginDelayedTransition(appBarLayout.parent as ViewGroup, it)
				appBarLayout.isVisible = !appBarLayout.isVisible
				binding.group.isVisible = !binding.group.isVisible
				binding.count.isVisible = !binding.count.isVisible
			}
			var flag =
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			if (!binding.group.isVisible) flag =
				flag or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
			binding.root.systemUiVisibility = flag
		}
		postponeEnterTransition()
		binding.root.doOnPreDraw { startPostponedEnterTransition() }
		binding.viewPager2.doOnPreDraw { viewModel.currentItem.value = args.position }
		viewModel.currentItem.observe(this) {
			toolbar.title = viewModel.adapter.currentList[it].name
		}
		viewModel.isEmpty.observe(this) { if (it) findNavController().navigateUp() }
	}

	override fun onBindMenu(menu: Menu) {
		menu.item<MenuItem>(R.id.open) {
			it.setOnClickListener { viewModel.setAction(ImageViewModel.Action.OPEN) }
		}
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
		if (resultCode == Activity.RESULT_OK && requestCode == 100) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				requireContext().contentResolver.delete(
					viewModel.adapter.currentList[binding.viewPager2.currentItem].uri,
					null,
					null
				)
				viewModel.refresh()
			}
		}
	}

	private fun favorite() {
		val checked = !((binding.favorite.tag as? Boolean) ?: false)
		val state = if (checked) intArrayOf(android.R.attr.state_checked) else intArrayOf()
		binding.favorite.setImageState(state, true)
		binding.favorite.tag = checked
	}

	private fun startEdit() {
		val image = viewModel.image
		Intent(Intent.ACTION_EDIT).apply {
			setDataAndType(image.uri, image.contentType)
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
		}.let(this::startActivity)
	}

	private fun startDelete() {
		val image = viewModel.image
		viewLifecycleOwner.lifecycle.coroutineScope.launch {
			get<ImageRepository>().delete(image) {
				startIntentSenderForResult(it, 100, null, 0, 0, 0, null)
			}
		}
	}

	private fun startShare() {
		val image = viewModel.image
		Intent(Intent.ACTION_SEND).run {
			setDataAndType(image.uri, image.contentType)
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			Intent.createChooser(this, "Share images...")
		}.let(this::startActivity)
	}

	private fun openOther() {
		val image = viewModel.image
		Intent(Intent.ACTION_VIEW).apply {
			setDataAndType(image.uri, image.contentType)
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
		}.let(this::startActivity)
	}

}
