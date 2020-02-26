package com.sorrowblue.cotlin.list

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sorrowblue.cotlin.list.databinding.ListFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FolderListFragment :
	DataBindingFragment<ListFragmentMainBinding>(R.layout.list_fragment_main) {

	private val viewModel: FolderListViewModel by viewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
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
				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		} else {
			// Permission has already been granted
			init()
		}
	}

	private fun init() {
		binding.viewModel = viewModel
		viewModel.adapter.listener = object : FolderListAdapter.OnClickListener {
			override fun onClick(folder: Folder, extras: FragmentNavigator.Extras) {
				findNavController().navigate(
					FolderListFragmentDirections.fileListFragment(folder),
					extras
				)
			}
		}
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
