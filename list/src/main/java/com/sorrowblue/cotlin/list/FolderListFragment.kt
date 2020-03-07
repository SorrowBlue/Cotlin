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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.list.databinding.ListFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.FabAction
import com.sorrowblue.cotlin.ui.view.applyVerticalInsets
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FolderListFragment :
	DataBindingFragment<ListFragmentMainBinding>(R.layout.list_fragment_main) {

	private val viewModel: FolderListViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//        sharedElementReturnTransition = MaterialContainerTransform(requireContext()).apply {
//            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
//        }


		val backward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, false)
		enterTransition = backward

		val forward = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Z, true)
		exitTransition = forward
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.recyclerView.applyVerticalInsets()
		if (checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
			if (shouldShowRequestPermissionRationale(requireActivity(), READ_EXTERNAL_STORAGE)) {
				ActivityCompat.requestPermissions(
					requireActivity(),
					arrayOf(READ_EXTERNAL_STORAGE),
					100
				)
			} else {
				MaterialAlertDialogBuilder(requireContext())
					.setTitle("権限必要")
					.setMessage("このアプリを使用するにはストレージの読み取り権限が必要です。")
					.setPositiveButton(android.R.string.ok) { _, _ ->
						startActivity(
							Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
								data = Uri.fromParts("package", requireActivity().packageName, null)
							}
						)
					}
					.show()
			}
		} else {
			init()
		}
	}

	private fun init() {
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
		binding.viewModel = viewModel
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
