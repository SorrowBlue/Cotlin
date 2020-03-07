package com.sorrowblue.cotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import com.sorrowblue.cotlin.databinding.PermissionFragmentBinding
import com.sorrowblue.cotlin.ui.fragment.ViewBindingFragment
import com.sorrowblue.cotlin.ui.fragment.appBarLayout
import com.sorrowblue.cotlin.ui.fragment.toolbar

internal class PermissionFragment :
	ViewBindingFragment<PermissionFragmentBinding>(PermissionFragmentBinding::inflate) {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.button.setOnClickListener {
			startActivity(
				Intent(
					Settings.ACTION_APPLICATION_DETAILS_SETTINGS
				).apply {
					data = Uri.fromParts("package", requireActivity().packageName, null)
				}
			)
		}
	}

	override fun onStart() {
		super.onStart()
		appBarLayout.isVisible = false
	}
}