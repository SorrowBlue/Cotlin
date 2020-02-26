package com.sorrowblue.cotlin.ui.activity

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hideSoftInput() {
    ActivityCompat.requireViewById<View>(this, android.R.id.content).windowToken?.let {
        ContextCompat.getSystemService(this, InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
