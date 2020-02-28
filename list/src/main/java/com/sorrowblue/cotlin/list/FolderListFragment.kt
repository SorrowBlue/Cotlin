package com.sorrowblue.cotlin.list

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sorrowblue.cotlin.list.FolderListFragmentDirections.Companion.actionFolderListFragmentToFileListFragment
import com.sorrowblue.cotlin.list.databinding.ListFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.FabAction
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FolderListFragment :
	DataBindingFragment<ListFragmentMainBinding>(R.layout.list_fragment_main) {

	private val viewModel: FolderListViewModel by viewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
		if (checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
			// Permission is not granted
			// Should we show an explanation?
			if (shouldShowRequestPermissionRationale(requireActivity(), READ_EXTERNAL_STORAGE)) {
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				ActivityCompat.requestPermissions(
					requireActivity(),
					arrayOf(READ_EXTERNAL_STORAGE),
					100
				)
			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(
					requireActivity(),
					arrayOf(READ_EXTERNAL_STORAGE),
					100
				)
				MaterialAlertDialogBuilder(requireContext())
					.setTitle("権限必要")
					.setMessage("a:pdkna]w md]awmd ]awdlam a:lwdm ]awmd pawkdあmwd：あkんMDパwDM」あwだま」pkwmd」あｐ")
					.setPositiveButton(android.R.string.ok) { _, _ ->
						startActivity(
							Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
								data = Uri.fromParts("package", requireActivity().packageName, null)
							}
						)
					}
					.show()
				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		} else {
			// Permission has already been granted
			init()
		}
		view.viewTreeObserver
			.addOnPreDrawListener {
				startPostponedEnterTransition()
				true
			}
	}

	private fun init() {
		binding.viewModel = viewModel
		viewModel.adapter.setOnClickListener { folder, position, extras ->
			findNavController().navigate(
				actionFolderListFragmentToFileListFragment(folder, position), extras
			)
		}
	}

	override val fabAction: FabAction?
		get() = R.drawable.ic_twotone_photo_camera to {
			startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
		}

	override fun onRequestPermissionsResult(
		requestCode: Int, permissions: Array<out String>, grantResults: IntArray
	) {
		if (requestCode == 100 && grantResults.contains(PERMISSION_GRANTED)) {
			init()
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshingForBinding(isRefreshing: Boolean?) {
	this.isRefreshing = isRefreshing ?: false
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.setOnRefreshListenerForBinding(listener: SwipeRefreshLayout.OnRefreshListener?) =
	setOnRefreshListener(listener)
