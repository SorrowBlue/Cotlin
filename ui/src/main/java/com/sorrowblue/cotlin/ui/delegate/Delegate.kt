package com.sorrowblue.cotlin.ui.delegate

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class UiDelegate<U : LifecycleOwner, T> : ReadOnlyProperty<U, T> {
	private var _value: T? = null
	abstract fun initialize(thisRef: U): T
	abstract fun getLifecycleOwner(thisRef: U): LifecycleOwner
	override fun getValue(thisRef: U, property: KProperty<*>) =
		_value ?: initialize(thisRef).also {
			_value = it
			getLifecycleOwner(thisRef).lifecycle.addObserver(object : LifecycleObserver {
				@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
				private fun onDestroy() {
					_value = null
					getLifecycleOwner(thisRef).lifecycle.removeObserver(this)
				}
			})
		}
}

