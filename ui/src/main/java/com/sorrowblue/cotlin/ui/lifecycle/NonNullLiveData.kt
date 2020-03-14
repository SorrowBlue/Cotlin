package com.sorrowblue.cotlin.ui.lifecycle

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class NonNullLiveData<T : Any>(private val defaultValue: T) : MutableLiveData<T>() {

	init {
		super.setValue(defaultValue)
	}

	override fun getValue() = super.getValue() ?: defaultValue

	@Deprecated(
		message = "リークする可能性があるため",
		replaceWith = ReplaceWith("observe(fragment, observer)"),
		level = DeprecationLevel.ERROR
	)
	override fun observe(owner: LifecycleOwner, observer: Observer<in T>) =
		super.observe(owner, observer)

	@Deprecated(
		message = "リークする可能性があるため",
		replaceWith = ReplaceWith("observe(fragment, onChanged)"),
		level = DeprecationLevel.ERROR
	)
	inline fun observe(owner: LifecycleOwner, crossinline onChanged: (T) -> Unit): Observer<T> =
		Observer<T> { t -> onChanged.invoke(t) }.also { super.observe(owner, it) }

	@MainThread
	inline fun observe(owner: Fragment, crossinline onChanged: (T) -> Unit): Observer<T> =
		Observer<T> { onChanged.invoke(it) }.also { super.observe(owner.viewLifecycleOwner, it) }

	@MainThread
	inline fun observe(owner: FragmentActivity, crossinline onChanged: (T) -> Unit): Observer<T> =
		Observer<T> { onChanged.invoke(it) }.also { super.observe(owner, it) }


	@Deprecated(
		message = "observe(LifecycleOwner,Observer<in T>) が非推奨のため",
		replaceWith = ReplaceWith("removeObservers(fragment)"),
		level = DeprecationLevel.ERROR
	)
	@MainThread
	override fun removeObservers(owner: LifecycleOwner) = super.removeObservers(owner)


//	@MainThread
//	inline fun observeForever(crossinline onChanged: (T) -> Unit): Observer<T> {
//		val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
//		super.observeForever(wrappedObserver)
//		return wrappedObserver
//	}

//	inline fun <V : Any> map(value: V, crossinline transform: (T) -> V): NonNullLiveData<V> {
//		val result = NonNullLiveData(value)
//		observeForever {
//			result.value = transform(it)
//		}
//		return result
//	}
}
