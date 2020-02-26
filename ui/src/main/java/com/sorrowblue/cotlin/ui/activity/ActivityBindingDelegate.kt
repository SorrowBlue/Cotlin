package com.sorrowblue.cotlin.ui.activity

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
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

fun <T : ViewDataBinding> FragmentActivity.dataBinding(@LayoutRes layoutResId: Int): ActivityBindingDelegate<T> =
	ActivityBindingDelegate(layoutResId)

class ActivityDataBindDelegate<T : ViewDataBinding> internal constructor(private val initView: () -> View) :
	ActivityDelegate<T>() {
	override fun initialize(thisRef: FragmentActivity): T =
		initView.invoke().dataBind<T>()!!.also { it.lifecycleOwner = thisRef }
}

fun <T : ViewDataBinding> FragmentActivity.dataBind(initView: () -> View): ActivityDataBindDelegate<T> =
	ActivityDataBindDelegate(initView)
