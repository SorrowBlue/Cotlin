package com.sorrowblue.cotlin.ui.fragment

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sorrowblue.cotlin.ui.R

val Fragment.fab: FloatingActionButton
	get() = ActivityCompat.requireViewById(
		requireActivity(),
		R.id.fab
	)

val Fragment.appBarLayout: AppBarLayout
	get() = ActivityCompat.requireViewById<AppBarLayout>(requireActivity(), R.id.app_bar_layout)

val Fragment.toolbar: Toolbar
	get() = ActivityCompat.requireViewById<Toolbar>(requireActivity(), R.id.toolbar)

fun FloatingActionButton.show(@DrawableRes resId: Int, action: () -> Unit) {
	setImageIcon(Icon.createWithResource(context, resId))
	setOnClickListener { action.invoke() }
	show()
}