package com.sorrowblue.cotlin.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

@BindingAdapter("drawerLockMode")
fun DrawerLayout.setDrawerLockModeForBinding(drawerLockMode: Int?) {
	setDrawerLockMode(drawerLockMode ?: DrawerLayout.LOCK_MODE_UNDEFINED)
}