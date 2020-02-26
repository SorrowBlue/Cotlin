package com.sorrowblue.cotlin.ui.delegate

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.appbar.AppBarLayout
import com.sorrowblue.cotlin.ui.R
import com.sorrowblue.cotlin.ui.fragment.FragmentDelegate
import com.sorrowblue.cotlin.ui.view.dataInflate

class ExtendsToolbarDelegate<T : ViewDataBinding>
internal constructor(@LayoutRes private val layoutResId: Int) : FragmentDelegate<T>() {
	override fun initialize(thisRef: Fragment): T =
		thisRef.requireAppBarLayout.dataInflate<T>(layoutResId).also {
			it.lifecycleOwner = thisRef.viewLifecycleOwner
			getLifecycleOwner(thisRef).lifecycle.addObserver(object : LifecycleObserver {
				@OnLifecycleEvent(Lifecycle.Event.ON_START)
				fun onStart() {
					(it.root.parent as? ViewGroup)?.removeView(it.root)
					thisRef.requireAppBarLayout.addView(it.root)
				}

				@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
				fun onStop() = thisRef.requireAppBarLayout.removeView(it.root)

				@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
				fun onDestroy() = thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
			})
		}

	private val Fragment.requireAppBarLayout: AppBarLayout
		get() = ActivityCompat.requireViewById(requireActivity(), R.id.app_bar_layout)
}

@Suppress("unused")
fun <T : ViewDataBinding> Fragment.extendsToolbar(@LayoutRes layoutResId: Int): ExtendsToolbarDelegate<T> =
	ExtendsToolbarDelegate(layoutResId)
