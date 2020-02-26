package com.sorrowblue.cotlin.ui.activity

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.sorrowblue.cotlin.ui.delegate.UiDelegate

abstract class ActivityDelegate<T> : UiDelegate<FragmentActivity, T>() {
	override fun getLifecycleOwner(thisRef: FragmentActivity): LifecycleOwner = thisRef
}
