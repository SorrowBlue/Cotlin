package com.sorrowblue.cotlin.ui.delegate

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.sorrowblue.cotlin.ui.activity.dataBinding

abstract class DataBindingActivity<T : ViewDataBinding>(@LayoutRes layoutId: Int) :
	AppCompatActivity() {
	protected val binding: T by dataBinding(layoutId)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding
	}
}
