package com.sorrowblue.cotlin.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.sorrowblue.cotlin.ui.R

abstract class DataBindingDialogFragment<B : ViewDataBinding>(@LayoutRes layoutId: Int) :
	DialogFragment() {

	protected val binding: B by dataBinding(layoutId)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	) = binding.root

	override fun onDismiss(dialog: DialogInterface) {
		super.onDismiss(dialog)
		findNavController().navigateUp()
	}
}
