package com.sorrowblue.cotlin.ui

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.sorrowblue.edc.ui.lifecycle.NonNullLiveData

class UiViewModel : ViewModel() {
	var isFullScreen
		get() = !isVisibleAppBarLayout.value && drawerLockMode.value == DrawerLayout.LOCK_MODE_LOCKED_CLOSED
		set(value) {
			isVisibleAppBarLayout.value = !value
			drawerLockMode.value =
				if (value) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED
		}

	val isVisibleAppBarLayout = NonNullLiveData(true)
	val drawerLockMode = NonNullLiveData(DrawerLayout.LOCK_MODE_UNLOCKED)
}