package com.sorrowblue.cotlin.futures.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.sorrowblue.cotlin.ui.UiViewModel
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sorrowblue.cotlin.futures.permission.databinding.PermissionFragmentMainBinding as FragmentBinding

internal class PermissionFragment :
	DataBindingFragment<FragmentBinding>(R.layout.permission_fragment_main) {

	private val viewModel: PermissionViewModel by viewModel()
	private val uiViewModel: UiViewModel by sharedViewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requestPermissions()
		binding.viewModel = viewModel
		viewModel.requestPermission = this::requestPermissions
		viewModel.goToSettings = {
			val intent = Intent(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				"package:${requireContext().packageName}".toUri()
			)
			startActivity(intent)
		}
		requireActivity().onBackPressedDispatcher.addCallback(this) { requireActivity().finishAffinity() }
		uiViewModel.isFullScreen = true
		appBarLayout.isVisible = false
	}

	override fun onStart() {
		super.onStart()
		if (PermissionChecker.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
			== PERMISSION_GRANTED
		) {
			requireActivity().recreate()
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		uiViewModel.isFullScreen = false
	}

	private fun requestPermissions() {
		requireActivity().registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
			if (it.all(Map.Entry<String, Boolean>::value)) {
				findNavController().popBackStack()
			} else {
				shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
			}
		}.launch(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
	}
}