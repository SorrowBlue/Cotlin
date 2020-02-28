package com.sorrowblue.cotlin.list

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import coil.api.load
import coil.transform.BlurTransformation
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.list.databinding.ListRecyclerViewItemFolderBinding as ItemBinding

internal class FolderListAdapter(context: Context) : DataBindAdapter<Folder, ItemBinding, FolderListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

	override fun areItemsTheSame(old: Folder, new: Folder) = old.name == new.name

	override fun areContentsTheSame(old: Folder, new: Folder) = old == new

	fun add(relativePath: String, image: Image) =
			currentList.find { it.name == relativePath }?.let { it.child += image }
					?: kotlin.run { currentList = currentList + Folder(relativePath, image) }

	private lateinit var listener: (folder: Folder, position: Int, extras: FragmentNavigator.Extras) -> Unit
	private val blurTransformation = BlurTransformation(context, 2f, 2f)

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Folder, ItemBinding>(parent, R.layout.list_recycler_view_item_folder) {
		override fun bind(value: Folder, position: Int) {
			ViewCompat.setTransitionName(binding.imageView, "folder_imageView_$position")
			binding.imageView.load(value.child.firstOrNull()?.uri) {
				transformations(blurTransformation)
			}
			binding.name.text = value.name
			binding.textView2.text = value.child.size.toString()
			binding.root.setOnClickListener {
				val extras = FragmentNavigatorExtras(binding.imageView to "folder_imageView_$position")
				listener.invoke(value, position, extras)
			}
		}
	}

	fun setOnClickListener(listener: (folder: Folder, position: Int, extras: FragmentNavigator.Extras) -> Unit) {
		this.listener = listener
	}
}
