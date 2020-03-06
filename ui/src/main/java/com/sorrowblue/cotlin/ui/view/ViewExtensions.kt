package com.sorrowblue.cotlin.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.sorrowblue.cotlin.ui.R
import com.google.android.material.R as MaterialR

val View.inflater: LayoutInflater get() = LayoutInflater.from(context)

fun <T : ViewDataBinding> View.dataBind(): T? = DataBindingUtil.bind(this)


val View.lifecycleOwner: LifecycleOwner?
	get() = ViewTreeLifecycleOwner.get(this)

fun <T : ViewDataBinding> ViewGroup.dataInflate(
	@LayoutRes layoutId: Int,
	attachToParent: Boolean = false
): T =
	DataBindingUtil.inflate(inflater, layoutId, this, attachToParent)

fun <T : ViewDataBinding> LayoutInflater.dataInflate(
	@LayoutRes layoutId: Int,
	attachToParent: Boolean = false
): T =
	DataBindingUtil.inflate(this, layoutId, null, attachToParent)

fun Int.colorInvert() =
	if (this == Color.TRANSPARENT
		|| 50 < (Color.red(this)
				* 0.299 + Color.green(this) * 0.587 + Color.blue(
			this
		) * 0.114) / 2.55
	) Color.BLACK else Color.WHITE


fun View.applySystemBarPaddingInsets() {
	this.doOnApplyWindowInsets { view, insets, padding, _ ->
		view.updatePadding(
			top = padding.top + insets.systemWindowInsetTop,
			left = insets.systemWindowInsetLeft,
			right = insets.systemWindowInsetRight
		)
	}
}

fun View.applySystemBarAndToolbarPaddingInsets() {
	this.doOnApplyWindowInsets { view, insets, padding, _ ->
		Log.d("APPAPP", "${context.resources.getDimensionPixelSize(com.google.android.material.R.dimen.action_bar_size)}")
		view.updatePadding(
			top = padding.top + insets.systemWindowInsetTop + context.resources.getDimensionPixelSize(com.google.android.material.R.dimen.action_bar_size),
			left = insets.systemWindowInsetLeft,
			right = insets.systemWindowInsetRight
		)
	}
}

fun View.applyNavigationBarPaddingInsets() =
	doOnApplyWindowInsets { view, insets, padding, _ ->
		view.updatePadding(
			bottom = padding.bottom + insets.systemWindowInsetBottom,
			left = insets.systemWindowInsetLeft,
			right = insets.systemWindowInsetRight
		)
	}

fun Guideline.applyNavigationBarPaddingInsets() =
	doOnApplyWindowInsets { view, insets, padding, _ ->
		this.setGuidelineEnd(insets.systemWindowInsetBottom)
	}

@SuppressLint("PrivateResource")
fun View.applyNavigationBarPaddingInsetsAndFabSize() {
	this.doOnApplyWindowInsets { view, insets, padding, _ ->
		view.updatePadding(
			bottom = padding.bottom + insets.systemWindowInsetBottom + context.resources.getDimensionPixelSize(
				MaterialR.dimen.design_fab_size_normal
			) + context.resources.getDimensionPixelSize(
				R.dimen.margin
			),
			left = insets.systemWindowInsetLeft,
			right = insets.systemWindowInsetRight
		)
	}
}

fun View.applyNavigationBarBottomMarginInsets() {
	doOnApplyWindowInsets { view, insets, _, margin ->
		view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
			setMargins(
				margin.left + insets.systemWindowInsetLeft,
				margin.top,
				margin.right + insets.systemWindowInsetRight,
				margin.bottom + insets.systemWindowInsetBottom
			)
		}
	}
}

fun View.applyVerticalInsets() {
	doOnApplyWindowInsets { view, windowInsets, initialPadding, _ ->
		view.updatePadding(
			top = initialPadding.top + windowInsets.systemWindowInsetTop + with(TypedValue().also {context.theme.resolveAttribute(android.R.attr.actionBarSize, it, true)}) {
				TypedValue.complexToDimensionPixelSize(this.data, resources.displayMetrics)
			},
			left = windowInsets.systemWindowInsetLeft,
			right = windowInsets.systemWindowInsetRight,
			bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom
		)
	}
}

/*********************
 * Implementation(s) *
 *********************/

private fun View.doOnApplyWindowInsets(f: (View, WindowInsets, InitialPadding, InitialMargin) -> Unit) {
	val initialPadding = recordInitialPaddingForView(this)
	val initialMargin = recordInitialMarginForView(this)
	setOnApplyWindowInsetsListener { v, insets ->
		insets.consumeSystemWindowInsets()
		f(v, insets, initialPadding, initialMargin)
		insets
	}
	requestApplyInsetsWhenAttached()
}

private class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)
private class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) =
	InitialPadding(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun recordInitialMarginForView(view: View) =
	InitialMargin(
		(view.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0,
		(view.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0,
		(view.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0,
		(view.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
	)

private fun View.requestApplyInsetsWhenAttached() {
	if (isAttachedToWindow) {
		requestApplyInsets()
	} else {
		addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
			override fun onViewAttachedToWindow(v: View) {
				v.removeOnAttachStateChangeListener(this)
				v.requestApplyInsets()
			}

			override fun onViewDetachedFromWindow(v: View) = Unit
		})
	}
}
