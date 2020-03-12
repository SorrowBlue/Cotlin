package com.sorrowblue.cotlin.futures.folder

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.view.applyVerticalInsets
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sorrowblue.cotlin.futures.folder.databinding.FolderFragmentMainBinding as FragmentBinding

internal class FolderFragment :
	DataBindingFragment<FragmentBinding>(R.layout.folder_fragment_main) {

	private val viewModel: FolderViewModel by viewModel()

	override val fabAction
		get() = R.drawable.ic_twotone_photo_camera to {
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
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
		binding.viewModel = viewModel
	}

}