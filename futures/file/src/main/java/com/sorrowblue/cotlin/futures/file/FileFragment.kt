package com.sorrowblue.cotlin.futures.file

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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

	override val fabAction = R.drawable.ic_twotone_photo_camera to {
		startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val forward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, true)
		enterTransition = forward
		val backward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, false)
		exitTransition = backward
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.recyclerView.applyVerticalInsets()
		binding.viewModel = viewModel
		ViewCompat.setTransitionName(binding.root, args.transitionName)
		postponeEnterTransition()
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
