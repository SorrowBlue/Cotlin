package com.sorrowblue.edc.ui.view

import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sorrowblue.cotlin.ui.R

@IntDef(Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class SnackbarDuration

fun FragmentActivity.snackbar(
	text: CharSequence, @SnackbarDuration duration: Int = Snackbar.LENGTH_SHORT,
	body: (Snackbar.() -> Unit)? = null
) = Snackbar.make(
	ActivityCompat.requireViewById(
		this,
		R.id.app_bar_layout
	), text, duration
)
	.also { body?.invoke(it) }.show()

fun FragmentActivity.snackbar(
	@StringRes resId: Int, @SnackbarDuration duration: Int = Snackbar.LENGTH_SHORT,
	body: (Snackbar.() -> Unit)? = null
) = Snackbar.make(
	ActivityCompat.requireViewById(
		this,
		R.id.content_main
	), resId, duration
)
	.also { body?.invoke(it) }.show()


fun Fragment.snackbar(
	@StringRes resId: Int, @SnackbarDuration duration: Int = Snackbar.LENGTH_SHORT,
	body: (Snackbar.() -> Unit)? = null
) = view?.let { v -> Snackbar.make(v, resId, duration).also { body?.invoke(it) }.show() } ?: Unit

fun Fragment.snackbar(
	text: CharSequence, @SnackbarDuration duration: Int = Snackbar.LENGTH_SHORT,
	body: (Snackbar.() -> Unit)? = null
) = view?.let { v -> Snackbar.make(v, text, duration).also { body?.invoke(it) }.show() } ?: Unit
