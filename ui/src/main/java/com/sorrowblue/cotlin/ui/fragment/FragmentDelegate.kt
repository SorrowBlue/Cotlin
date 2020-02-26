package com.sorrowblue.cotlin.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.sorrowblue.cotlin.ui.delegate.UiDelegate

abstract class FragmentDelegate<T> : UiDelegate<Fragment, T>() {
	override fun getLifecycleOwner(thisRef: Fragment): LifecycleOwner = thisRef.viewLifecycleOwner
}
