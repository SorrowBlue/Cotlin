package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.list.FileListFragmentDirections.Companion.actionFileListFragmentToImageFragment
import com.sorrowblue.cotlin.list.databinding.ListFragmentFileBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.toolbar
import com.sorrowblue.cotlin.ui.view.applyNavigationBarPaddingInsets
import com.sorrowblue.cotlin.ui.view.applySystemBarAndToolbarPaddingInsets
import com.sorrowblue.cotlin.ui.view.applyVerticalInsets

internal class FileListFragment :
	DataBindingFragment<ListFragmentFileBinding>(R.layout.list_fragment_file) {

	private val adapter = FileListAdapter()
	private val args: FileListFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
//			fadeMode = MaterialContainerTransform.FADE_MODE_IN
//			isDrawDebugEnabled = true
//		}
		val forward =  MaterialSharedAxis.create(requireContext(),  MaterialSharedAxis.Z,  true)
		enterTransition = forward

		val backward =  MaterialSharedAxis.create(requireContext(),  MaterialSharedAxis.Z,  false)
		exitTransition = backward
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()
		adapter.setList(args.folder.child)
		binding.recyclerView.applyVerticalInsets()
		binding.recyclerView.adapter = adapter
		ViewCompat.setTransitionName(binding.recyclerView, args.transitionName)
		adapter.setOnClickListener { image, position, extras ->
			findNavController().navigate(
				actionFileListFragmentToImageFragment(args.folder.child.toTypedArray(), position),
				extras
			)
		}
		binding.recyclerView.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
	}

	override fun onStart() {
		super.onStart()
		toolbar.title = args.folder.name
	}
}
