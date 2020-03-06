package com.sorrowblue.cotlin

import android.os.Bundle
import android.transition.TransitionManager
import android.view.Menu
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.transition.MaterialFade
import com.sorrowblue.cotlin.databinding.ActivityMainBinding
import com.sorrowblue.cotlin.ui.delegate.DataBindingActivity
import com.sorrowblue.cotlin.ui.view.applyNavigationBarBottomMarginInsets
import com.sorrowblue.cotlin.ui.view.applyNavigationBarPaddingInsetsAndFabSize
import com.sorrowblue.cotlin.ui.view.applySystemBarPaddingInsets

class MainActivity : DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

	private lateinit var appBarConfiguration: AppBarConfiguration

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(binding.appBarMain.toolbar)
		val drawerLayout: DrawerLayout = binding.drawerLayout
		val navView: NavigationView = binding.navView
		navView.getHeaderView(0).applySystemBarPaddingInsets()
		val navController = findNavController(R.id.nav_host_fragment)
		appBarConfiguration = AppBarConfiguration(setOf(R.id.folderListFragment), drawerLayout)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
		contentView.systemUiVisibility = FULL_SCREEN
		binding.appBarMain.fab.applyNavigationBarBottomMarginInsets()
		binding.navView.applySystemBarPaddingInsets()
		binding.appBarMain.appBarLayout.applySystemBarPaddingInsets()

	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	private val contentView get() = ActivityCompat.requireViewById<View>(this, android.R.id.content)
}

const val FULL_SCREEN =
	View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
