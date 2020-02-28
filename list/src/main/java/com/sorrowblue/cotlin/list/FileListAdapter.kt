package com.sorrowblue.cotlin.list

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import coil.api.load
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.list.databinding.ListRecyclerViewItemFileBinding as ItemBinding

internal class FileListAdapter : DataBindAdapter<Image, ItemBinding, FileListAdapter.ViewHolder>() {

	fun setList(list: List<Image>) {
		currentList = list
	}

	private lateinit var listener: (Image, Int, FragmentNavigator.Extras) -> Unit

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
	override fun areItemsTheSame(old: Image, new: Image) = old.uri == new.uri
	override fun areContentsTheSame(old: Image, new: Image) = old == new

	fun setOnClickListener(action: (Image, Int, FragmentNavigator.Extras) -> Unit) {
		this.listener = action
	}

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Image, ItemBinding>(
		parent, R.layout.list_recycler_view_item_file
	) {
		override fun bind(value: Image, position: Int) {
			binding.imageView.load(currentList[position].uri) {
				placeholder(R.drawable.list_ic_twotone_folder)
			}
			ViewCompat.setTransitionName(binding.imageView, "imageView_$position")
			binding.imageView.setOnClickListener {
				val extras = FragmentNavigatorExtras(it to "imageView_$position")
				listener.invoke(currentList[position], position, extras)
			}
		}
	}
}
