package com.sorrowblue.cotlin.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sorrowblue.cotlin.ui.R

abstract class ViewBindingDialogFragment<T : ViewBinding>(init: (LayoutInflater) -> T) :
	DialogFragment() {
	protected val binding: T by viewBinding(init)
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
