package com.sorrowblue.cotlin.ui.recyclerview

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sorrowblue.cotlin.ui.view.inflater
import kotlin.properties.Delegates


abstract class DataBindAdapter<T, B : ViewDataBinding, VH : DataBindAdapter.ViewHolder<T, B>> :
	RecyclerView.Adapter<VH>() {
	var currentList: List<T> by Delegates.observable(emptyList()) { _, old, new ->
		DiffUtil.calculateDiff(
			DiffCallback(old, new)
		).dispatchUpdatesTo(this)
	}

	override fun getItemCount() = currentList.size
	override fun onBindViewHolder(holder: VH, position: Int) =
		holder.bind(currentList[position], position)

	abstract fun areItemsTheSame(old: T, new: T): Boolean
	abstract fun areContentsTheSame(old: T, new: T): Boolean
	abstract class ViewHolder<T, B : ViewDataBinding>(protected val binding: B) :
		RecyclerView.ViewHolder(binding.root) {
		constructor(parent: ViewGroup, layoutId: Int) :
				this(DataBindingUtil.inflate<B>(parent.inflater, layoutId, parent, false))

		abstract fun bind(value: T, position: Int)
	}

	private inner class DiffCallback(private val old: List<T>, private val new: List<T>) :
		DiffUtil.Callback() {
		override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
			this@DataBindAdapter.areContentsTheSame(old[oldItemPosition], new[newItemPosition])

		override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
			this@DataBindAdapter.areItemsTheSame(old[oldItemPosition], new[newItemPosition])

		override fun getOldListSize() = old.size
		override fun getNewListSize() = new.size
	}
}
