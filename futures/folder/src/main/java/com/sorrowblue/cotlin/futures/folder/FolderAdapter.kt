package com.sorrowblue.cotlin.futures.folder

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.futures.file.FileFragmentArgs
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.futures.folder.databinding.FolderRecyclerViewItemMainBinding as ItemBinding

internal class FolderAdapter : DataBindAdapter<Folder, ItemBinding, FolderAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
	override fun areItemsTheSame(old: Folder, new: Folder) = old.name == new.name
	override fun areContentsTheSame(old: Folder, new: Folder) = old == new

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Folder, ItemBinding>(
		parent, R.layout.folder_recycler_view_item_main
	) {
		override fun bind(value: Folder, position: Int) {
			binding.folder = value
			ViewCompat.setTransitionName(binding.root, "folder_$position")
			binding.root.setOnClickListener {
				binding.root.findNavController().navigate(
					FolderFragmentDirections.actionToFileNavigation().actionId,
					FileFragmentArgs(value, it.transitionName).toBundle(),
					null,
					FragmentNavigatorExtras(it to it.transitionName)
				)
			}
			binding.executePendingBindings()
		}
	}
}
