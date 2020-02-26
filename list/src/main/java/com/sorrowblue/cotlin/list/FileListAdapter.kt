package com.sorrowblue.cotlin.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.sorrowblue.cotlin.ui.view.inflater
import kotlin.properties.Delegates
import com.sorrowblue.cotlin.list.databinding.ListRecyclerViewItemFileBinding as ItemBinding

private class FileListDiffCallback(
	private val old: List<Image>,
	private val new: List<Image>
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

internal class FileListAdapter : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

	var currentList: List<Image> by Delegates.observable(emptyList()) { _, old, new ->
		DiffUtil.calculateDiff(FileListDiffCallback(old, new)).dispatchUpdatesTo(this)
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
		holder.binding.imageView.load(currentList[position].uri) {
			placeholder(R.drawable.list_ic_twotone_folder)
		}
	}
}
