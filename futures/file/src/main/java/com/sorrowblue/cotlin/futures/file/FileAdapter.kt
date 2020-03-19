package com.sorrowblue.cotlin.futures.file

import android.content.Context
import android.view.ViewGroup
import androidx.navigation.findNavController
import coil.transform.BlurTransformation
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.image.Image
import com.sorrowblue.cotlin.futures.image.ImageFragmentArgs
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.futures.file.databinding.FileRecyclerViewItemMainBinding as ItemBinding

internal class FileAdapter(context: Context,  private val folder: Folder) : DataBindAdapter<Image, ItemBinding, FileAdapter.ViewHolder>() {

	private val transformation = BlurTransformation(context, 1.5f, 1.5f)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
	override fun areItemsTheSame(old: Image, new: Image) = old.uri == new.uri
	override fun areContentsTheSame(old: Image, new: Image) = old == new

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Image, ItemBinding>(
		parent, R.layout.file_recycler_view_item_main
	) {
		override fun bind(value: Image, position: Int) {
			binding.image = value
			binding.transformation  = transformation
			binding.root.setOnClickListener {
				it.findNavController().navigate(
					FileFragmentDirections.actionToImageNavigation().actionId,
					ImageFragmentArgs(folder.copy(child = currentList.toMutableList()), position).toBundle()
				)
			}
			binding.executePendingBindings()
		}
	}
}
