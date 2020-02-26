package com.sorrowblue.edc.ui.view

import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

@IntDef(Toast.LENGTH_LONG, Toast.LENGTH_SHORT)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastDuration

fun FragmentActivity.toast(
	@StringRes resId: Int, @ToastDuration duration: Int = Toast.LENGTH_SHORT,
	body: (Toast.() -> Unit)? = null
) = Toast.makeText(this, resId, duration).also { body?.invoke(it) }.show()

fun FragmentActivity.toast(
	text: CharSequence, @ToastDuration duration: Int = Toast.LENGTH_SHORT,
	body: (Toast.() -> Unit)? = null
) = Toast.makeText(this, text, duration).also { body?.invoke(it) }.show()

fun Fragment.toast(
	@StringRes resId: Int, @ToastDuration duration: Int = Toast.LENGTH_SHORT,
	body: (Toast.() -> Unit)? = null
) = Toast.makeText(requireContext(), resId, duration).also { body?.invoke(it) }.show()

fun Fragment.toast(
	text: CharSequence, @ToastDuration duration: Int = Toast.LENGTH_SHORT,
	body: (Toast.() -> Unit)? = null
) = Toast.makeText(requireContext(), text, duration).also { body?.invoke(it) }.show()
