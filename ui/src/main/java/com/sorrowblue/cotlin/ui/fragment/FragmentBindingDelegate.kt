package com.sorrowblue.cotlin.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sorrowblue.cotlin.ui.view.dataInflate
import com.sorrowblue.cotlin.ui.view.inflater

class FragmentBindingDelegate<T : ViewDataBinding>
internal constructor(@LayoutRes private val layoutId: Int) : FragmentDelegate<T>() {

	override fun initialize(thisRef: Fragment): T =
		thisRef.dataInflate.also { it.lifecycleOwner = thisRef.viewLifecycleOwner }

	private val Fragment.dataInflate: T
		get() = ActivityCompat.requireViewById<ViewGroup>(
			requireActivity(),
			requireParentFragment().id
		).dataInflate(layoutId)
}

class FragmentViewBindingDelegate<T : ViewBinding>
internal constructor(private val init: (LayoutInflater) -> T) : FragmentDelegate<T>() {

	override fun initialize(thisRef: Fragment): T = thisRef.dataInflate

	private val Fragment.dataInflate: T
		get() = ActivityCompat.requireViewById<ViewGroup>(
			requireActivity(),
			requireParentFragment().id
		).inflater.let(init::invoke)
}

fun <T : ViewDataBinding> Fragment.dataBinding(@LayoutRes layoutId: Int): FragmentBindingDelegate<T> =
	FragmentBindingDelegate(layoutId)

fun <T : ViewBinding> Fragment.viewBinding(init: (LayoutInflater) -> T): FragmentViewBindingDelegate<T> =
	FragmentViewBindingDelegate(init)
