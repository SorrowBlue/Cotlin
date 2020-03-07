package com.sorrowblue.cotlin.ui.view

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sorrowblue.cotlin.ui.R
import androidx.appcompat.R as AppcompatR
import com.google.android.material.R as MaterialR

val View.inflater: LayoutInflater get() = LayoutInflater.from(context)

fun <T : ViewDataBinding> View.dataBind(): T? = DataBindingUtil.bind(this)

fun <T : ViewDataBinding> ViewGroup.dataInflate(
	@LayoutRes layoutId: Int, attachToParent: Boolean = false
): T = DataBindingUtil.inflate(inflater, layoutId, this, attachToParent)

fun <T : ViewDataBinding> LayoutInflater.dataInflate(
	@LayoutRes layoutId: Int, attachToParent: Boolean = false
): T = DataBindingUtil.inflate(this, layoutId, null, attachToParent)

fun Int.colorInvert(): Int {
	val r = Color.red(this) * 0.299
	val g = Color.green(this) * 0.587
	val b = Color.blue(this) * 0.114
	return if (this == Color.TRANSPARENT || 50 < (r + g + b) / 2.55) Color.BLACK else Color.WHITE
}

fun View.applySystemBarPaddingInsets() = doOnApplyWindowInsets { view, insets, padding, _ ->
	view.updatePadding(
		top = padding.top + insets.systemWindowInsetTop,
		left = insets.systemWindowInsetLeft,
		right = insets.systemWindowInsetRight
	)
}

fun View.applyNavigationBarPaddingInsets() = doOnApplyWindowInsets { view, insets, padding, _ ->
	view.updatePadding(
		bottom = padding.bottom + insets.systemWindowInsetBottom,
		left = insets.systemWindowInsetLeft,
		right = insets.systemWindowInsetRight
	)
}

@SuppressLint("PrivateResource")
fun View.applyNavigationBarPaddingInsetsAndFabSize() {
	this.doOnApplyWindowInsets { view, insets, padding, _ ->
		val fabSize =
			context.resources.getDimensionPixelSize(MaterialR.dimen.design_fab_size_normal)
		val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
		view.updatePadding(
			bottom = padding.bottom + insets.systemWindowInsetBottom + fabSize + margin,
			left = insets.systemWindowInsetLeft,
			right = insets.systemWindowInsetRight
		)
	}
}

fun View.applyNavigationBarBottomMarginInsets() = doOnApplyWindowInsets { view, insets, _, margin ->
	view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
		updateMargins(
			bottom = margin.bottom + insets.systemWindowInsetBottom,
			left = margin.left + insets.systemWindowInsetLeft,
			right = margin.right + insets.systemWindowInsetRight
		)
	}
}

fun View.applyVerticalInsets() = doOnApplyWindowInsets { view, windowInsets, initialPadding, _ ->
	val actionBarSize =
		context.theme.resolveDimensionPixelSize(AppcompatR.attr.actionBarSize, true)
	view.updatePadding(
		top = initialPadding.top + windowInsets.systemWindowInsetTop + actionBarSize,
		left = windowInsets.systemWindowInsetLeft,
		right = windowInsets.systemWindowInsetRight,
		bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom
	)
}

/**
 * Implements
 */

private class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)
private class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun Resources.Theme.resolveDimensionPixelSize(@AttrRes attr: Int, resolveRefs: Boolean) =
	TypedValue().let {
		resolveAttribute(attr, it, resolveRefs)
		TypedValue.complexToDimensionPixelSize(it.data, resources.displayMetrics)
	}

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
