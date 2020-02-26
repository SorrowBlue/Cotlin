package com.sorrowblue.cotlin.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.sorrowblue.cotlin.list.databinding.ListFragmentMainBinding
import com.sorrowblue.cotlin.ui.fragment.DataBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FolderListFragment :
	DataBindingFragment<ListFragmentMainBinding>(R.layout.list_fragment_main) {

	private val viewModel: FolderListViewModel by viewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewLifecycleOwner.lifecycle.addObserver(viewModel)
		binding.viewModel = viewModel
		viewModel.adapter.listener = object : FolderListAdapter.OnClickListener {
			override fun onClick(folder: Folder, extras: FragmentNavigator.Extras) {
				findNavController().navigate(
					FolderListFragmentDirections.fileListFragment(folder),
					extras
				)
			}
		}
	}
}
