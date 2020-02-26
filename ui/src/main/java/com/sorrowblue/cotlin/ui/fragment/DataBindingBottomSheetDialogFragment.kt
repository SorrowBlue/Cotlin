package com.sorrowblue.cotlin.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class DataBindingBottomSheetDialogFragment<B : ViewDataBinding>(@LayoutRes layoutId: Int) :
	BottomSheetDialogFragment() {
	protected val binding: B by dataBinding(layoutId)
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	) = binding.root

	override fun onDismiss(dialog: DialogInterface) {
		super.onDismiss(dialog)
		findNavController().navigateUp()
	}
}
