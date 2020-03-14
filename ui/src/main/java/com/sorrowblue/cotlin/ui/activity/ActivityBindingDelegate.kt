package com.sorrowblue.cotlin.ui.activity

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.sorrowblue.cotlin.ui.view.dataBind
import com.sorrowblue.cotlin.ui.view.dataInflate

class ActivityBindingDelegate<T : ViewDataBinding> internal constructor(@LayoutRes private val layoutResId: Int) :
	ActivityDelegate<T>() {
	override fun initialize(thisRef: FragmentActivity): T =
		thisRef.layoutInflater.dataInflate<T>(layoutResId).also {
			thisRef.setContentView(it.root)
			it.lifecycleOwner = thisRef
		}
}


class ActivityViewBindingDelegate<T : ViewBinding>
internal constructor(private val init: (LayoutInflater) -> T) : ActivityDelegate<T>() {
	override fun initialize(thisRef: FragmentActivity) = init.invoke(thisRef.layoutInflater)
}

fun <T : ViewDataBinding> FragmentActivity.dataBinding(@LayoutRes layoutResId: Int): ActivityBindingDelegate<T> =
	ActivityBindingDelegate(layoutResId)

fun <T : ViewDataBinding> FragmentActivity.dataBinding(initView: () -> View): ActivityDataBindDelegate<T> =
	ActivityDataBindDelegate(initView)

class ActivityDataBindDelegate<T : ViewDataBinding> internal constructor(private val initView: () -> View) :
	ActivityDelegate<T>() {
	override fun initialize(thisRef: FragmentActivity): T =
		initView.invoke().dataBind<T>()!!.also { it.lifecycleOwner = thisRef }
}

fun <T : ViewBinding> FragmentActivity.dataBind(init: (LayoutInflater) -> T): ActivityViewBindingDelegate<T> =
	ActivityViewBindingDelegate(init)
