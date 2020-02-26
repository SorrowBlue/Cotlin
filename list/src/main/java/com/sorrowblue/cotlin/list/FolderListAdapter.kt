package com.sorrowblue.cotlin.list

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.BlurTransformation
import com.sorrowblue.cotlin.ui.view.inflater
import kotlin.properties.Delegates
import com.sorrowblue.cotlin.list.databinding.ListRecyclerViewItemFolderBinding as ItemBinding

private class DiffCallback(
	private val old: List<Folder>,
	private val new: List<Folder>
) : DiffUtil.Callback() {
	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return old[oldItemPosition].name == new[newItemPosition].name
	}

	override fun getOldListSize(): Int {
		return old.size
	}

	override fun getNewListSize(): Int {
		return new.size
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return old[oldItemPosition] == new[newItemPosition]
	}

}

internal class FolderListAdapter : RecyclerView.Adapter<FolderListAdapter.ViewHolder>() {

	var currentList: List<Folder> by Delegates.observable(emptyList()) { _, old, new ->
		DiffUtil.calculateDiff(DiffCallback(old, new)).dispatchUpdatesTo(this)
	}

	class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
		constructor(parent: ViewGroup) : this(ItemBinding.inflate(parent.inflater, parent, false))
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(parent)
	}

	override fun getItemCount(): Int {
		return currentList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.binding.imageView.load(currentList[position].child.firstOrNull()?.uri) {
			transformations(
				BlurTransformation(
					context = holder.binding.imageView.context,
					radius = 2f,
					sampling = 2f
				)
			)
		}
		holder.binding.root.setOnClickListener {
			ViewCompat.setTransitionName(holder.binding.root, "root")
			listener.onClick(
				currentList[position],
				FragmentNavigatorExtras(holder.binding.root to "root")
			)
		}
		holder.binding.name.text = currentList[position].name
		holder.binding.textView2.text = currentList[position].child.size.toString()
	}

	lateinit var listener: OnClickListener

	interface OnClickListener {
		fun onClick(folder: Folder, extras: FragmentNavigator.Extras)
	}
}
