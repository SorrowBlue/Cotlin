package com.sorrowblue.cotlin.futures.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.addCallback
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

const val REQUEST_READ_EXTERNAL_STORAGE = 100

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

	override fun onRequestPermissionsResult(
		requestCode: Int, permissions: Array<out String>, grantResults: IntArray
	) {
		when {
			requestCode != REQUEST_READ_EXTERNAL_STORAGE ->
				super.onRequestPermissionsResult(requestCode, permissions, grantResults)
			grantResults.firstOrNull() == PERMISSION_GRANTED ->
				findNavController().popBackStack()
			else -> viewModel.shouldShowDetails.value =
				shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
		}
	}

	private fun requestPermissions() =
		requestPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
}