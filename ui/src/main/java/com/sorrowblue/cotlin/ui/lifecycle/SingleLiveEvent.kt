package com.sorrowblue.edc.ui.lifecycle

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T : Any>(value: T? = null) : MutableLiveData<T>(value) {

	val pending = AtomicBoolean(false)

	@Deprecated(
		"You must use 'observe (Fragment) {}' or 'observe (FragmentActivity) {}' because it is issued multiple times.",
		ReplaceWith("observe(owner) {}"),
		DeprecationLevel.ERROR
	)
	override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
		observe(owner) {
			if (pending.compareAndSet(true, false) && it != null) {
				observer.onChanged(it)
			}
		}
	}

	@MainThread
	inline fun observe(owner: Fragment, crossinline onChanged: (T) -> Unit) =
		Observer<T> {
			if (pending.compareAndSet(true, false) && it != null) {
				onChanged.invoke(it)
			}
		}.also { super.observe(owner.viewLifecycleOwner, it) }

	@MainThread
	inline fun observeNullable(owner: Fragment, crossinline onChanged: (T?) -> Unit) =
		Observer<T?> {
			if (pending.compareAndSet(true, false)) {
				onChanged.invoke(it)
			}
		}.also { super.observe(owner.viewLifecycleOwner, it) }

	@MainThread
	inline fun observe(owner: FragmentActivity, crossinline onChanged: (T) -> Unit) =
		Observer<T> {
			if (pending.compareAndSet(true, false) && it != null) {
				onChanged.invoke(it)
			}
		}.also { super.observe(owner, it) }


	@MainThread
	override fun setValue(t: T?) {
		pending.set(true)
		super.setValue(t)
	}

	@MainThread
	fun call() {
		value = null
	}
}
