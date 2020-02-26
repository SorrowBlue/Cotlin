package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.sorrowblue.cotlin.list.databinding.ListFragmentFileBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import com.sorrowblue.cotlin.ui.fragment.toolbar

internal class FileListFragment :
	DataBindingFragment<ListFragmentFileBinding>(R.layout.list_fragment_file) {

	private val adapter = FileListAdapter()
	private val args: FileListFragmentArgs by navArgs()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sharedElementEnterTransition = TransitionInflater.from(requireContext())
			.inflateTransition(android.R.transition.move)
			.addListener(object : Transition.TransitionListener {
				override fun onTransitionEnd(transition: Transition) {
					binding.root.background = null
				}

				override fun onTransitionResume(transition: Transition) {}
				override fun onTransitionPause(transition: Transition) {}
				override fun onTransitionCancel(transition: Transition) {}
				override fun onTransitionStart(transition: Transition) {}

			})
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		adapter.currentList += args.folder.child
		binding.recyclerView.adapter = adapter
		ViewCompat.setTransitionName(binding.root, "root")
	}

	override fun onStart() {
		super.onStart()
		toolbar.title = args.folder.name
	}
}
