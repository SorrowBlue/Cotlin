package com.sorrowblue.cotlin.futures.permission

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class PermissionViewModel : ViewModel() {

	val shouldShowDetails = MutableLiveData(true)

	lateinit var requestPermission: () -> Unit
	lateinit var goToSettings: () -> Unit
}