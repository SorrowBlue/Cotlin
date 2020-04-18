package com.sorrowblue.cotlin

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sorrowblue.cotlin.ui.activity.ActivityDelegate

internal fun AppCompatActivity.navController() = NavControllerDelegate()

internal class NavControllerDelegate : ActivityDelegate<NavController>() {
	override fun initialize(thisRef: FragmentActivity) =
		thisRef.findNavController(R.id.nav_host_fragment)
}