package com.sorrowblue.cotlin.ui.view

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes


inline fun <reified T : MenuItem> Menu.item(@IdRes resId: Int, noinline action: (T) -> Unit) {
	(findItem(resId) as? T)?.let(action::invoke)
}

inline fun MenuItem.setOnClickListener(crossinline action: () -> Unit) {
	setOnMenuItemClickListener {
		action.invoke()
		true
	}
}