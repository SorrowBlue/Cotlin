package com.sorrowblue.cotlin

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sorrowblue.cotlin.databinding.ActivityMainBinding
import com.sorrowblue.cotlin.ui.UiViewModel
import com.sorrowblue.cotlin.ui.delegate.DataBindingActivity
import com.sorrowblue.cotlin.ui.view.applyNavigationBarBottomMarginInsets
import com.sorrowblue.cotlin.ui.view.applySystemBarPaddingInsets
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private val uiViewModel: UiViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding.uiViewModel = uiViewModel
		binding.lifecycleOwner =  this
		if (PermissionChecker.checkSelfPermission(
				this,
				READ_EXTERNAL_STORAGE
			) != PermissionChecker.PERMISSION_GRANTED
		) {
			val navController = findNavController(R.id.nav_host_fragment)
			navController.navigate(R.id.permission_navigation)
		}
		setSupportActionBar(binding.appBarMain.toolbar)
		applyFullScreen()
		val navController = findNavController(R.id.nav_host_fragment)
		appBarConfiguration =
			AppBarConfiguration(setOf(R.id.folderFragment), binding.drawerLayout)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.navView.setupWithNavController(navController)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	private val contentView get() = ActivityCompat.requireViewById<View>(this, android.R.id.content)

	private fun applyFullScreen() {
		contentView.systemUiVisibility = FULL_SCREEN
		binding.navView.getHeaderView(0).applySystemBarPaddingInsets()
		binding.appBarMain.fab.applyNavigationBarBottomMarginInsets()
		binding.appBarMain.appBarLayout.applySystemBarPaddingInsets()
	}
}

const val FULL_SCREEN =
	View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
