package com.sorrowblue.cotlin.futures.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import com.sorrowblue.cotlin.futures.permission.databinding.PermissionFragmentMainBinding as FragmentBinding

internal class PermissionFragment :
	DataBindingFragment<FragmentBinding>(R.layout.permission_fragment_main) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 100)
	}
	override fun onStart() {
		super.onStart()
		appBarLayout.isVisible = false
		if (ContextCompat.checkSelfPermission(
				requireContext(),
				READ_EXTERNAL_STORAGE
			) == PERMISSION_GRANTED
		) return requireActivity().recreate()
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		requireActivity().onBackPressedDispatcher.addCallback(this) {
			requireActivity().finishAffinity()
		}
	}

	override fun onRequestPermissionsResult(
		requestCode: Int, permissions: Array<out String>, grantResults: IntArray
	) {
		if (requestCode == 100) {
			if (grantResults[0] == PERMISSION_GRANTED) {
				Log.d(javaClass.simpleName, "Permission OK")
				findNavController().popBackStack()
			} else {
				Log.d(javaClass.simpleName, "Permission NO")
				if (ActivityCompat.shouldShowRequestPermissionRationale(
						requireActivity(),
						READ_EXTERNAL_STORAGE
					)
				) {
					binding.actionButton.apply {
						setText(R.string.permission_label_permission)
						setOnClickListener {
							requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 100)
						}
					}
				} else {
					binding.actionButton.apply {
						setText(R.string.permission_label_to_settings)
						setOnClickListener {
							startActivity(
								Intent(
									Settings.ACTION_APPLICATION_DETAILS_SETTINGS
								).apply {
									data = Uri.fromParts(
										"package",
										requireActivity().packageName,
										null
									)
								}
							)
						}
					}
				}
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}
}