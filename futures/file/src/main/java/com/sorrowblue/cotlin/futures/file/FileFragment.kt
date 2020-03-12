package com.sorrowblue.cotlin.futures.file

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.toolbar
import com.sorrowblue.cotlin.ui.view.applyVerticalInsets
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import com.sorrowblue.cotlin.futures.file.databinding.FileFragmentMainBinding as FragmentBinding

internal class FileFragment : DataBindingFragment<FragmentBinding>(R.layout.file_fragment_main) {

	private val args: FileFragmentArgs by navArgs()
	private val viewModel: FileViewModel by viewModel { parametersOf(args.folder) }

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
		binding.recyclerView.applyVerticalInsets()
		binding.viewModel = viewModel
//		ViewCompat.setTransitionName(binding.recyclerView, args.transitionName)
//		adapter.setOnClickListener { image, position, extras ->
//			findNavController().navigate(
//				actionFileListFragmentToImageFragment(args.folder.child.toTypedArray(), position),
//				extras
//			)
//		}
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
