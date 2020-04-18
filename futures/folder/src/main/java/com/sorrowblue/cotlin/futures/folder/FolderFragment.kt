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
import com.sorrowblue.cotlin.ui.R as UiR

internal class FolderFragment :
	DataBindingFragment<FragmentBinding>(R.layout.folder_fragment_main) {

	private val viewModel: FolderViewModel by viewModel()

	override val fabAction = UiR.drawable.ic_twotone_photo_camera to {
		startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enterTransition = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, true)
		exitTransition = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.recyclerView.applyVerticalInsets()
		binding.viewModel = viewModel
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
	}
}