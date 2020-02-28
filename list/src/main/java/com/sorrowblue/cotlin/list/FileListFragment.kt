package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.sorrowblue.cotlin.list.FileListFragmentDirections.Companion.actionFileListFragmentToImageFragment
import com.sorrowblue.cotlin.list.databinding.ListFragmentFileBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.toolbar

internal class FileListFragment :
	DataBindingFragment<ListFragmentFileBinding>(R.layout.list_fragment_file) {

	private val adapter = FileListAdapter()
	private val args: FileListFragmentArgs by navArgs()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sharedElementEnterTransition = MaterialContainerTransform(requireContext())
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition()
		adapter.setList(args.folder.child)
		binding.recyclerView.adapter = adapter
		ViewCompat.setTransitionName(binding.root, "folder_imageView_${args.position}")
		adapter.setOnClickListener { image, position, extras ->
			findNavController().navigate(
				actionFileListFragmentToImageFragment(image.uri, position), extras
			)
		}
		view.viewTreeObserver.addOnPreDrawListener {
			startPostponedEnterTransition()
			true
		}
	}

	override fun onStart() {
		super.onStart()
		toolbar.title = args.folder.name
	}
}
