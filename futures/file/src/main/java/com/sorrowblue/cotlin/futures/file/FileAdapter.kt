package com.sorrowblue.cotlin.futures.file

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import coil.transform.BlurTransformation
import com.sorrowblue.cotlin.domains.image.Image
import com.sorrowblue.cotlin.futures.image.ImageFragmentArgs
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.futures.file.databinding.FileRecyclerViewItemMainBinding as ItemBinding

internal class FileAdapter(context: Context) : DataBindAdapter<Image, ItemBinding, FileAdapter.ViewHolder>() {

	fun setList(list: List<Image>) {
		currentList = list
	}
	private val transformation = BlurTransformation(context, 1f, 1f)
	private lateinit var listener: (Image, Int, FragmentNavigator.Extras) -> Unit

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
	override fun areItemsTheSame(old: Image, new: Image) = old.uri == new.uri
	override fun areContentsTheSame(old: Image, new: Image) = old == new

	fun setOnClickListener(action: (Image, Int, FragmentNavigator.Extras) -> Unit) {
		this.listener = action
	}

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Image, ItemBinding>(
		parent, R.layout.file_recycler_view_item_main
	) {
		override fun bind(value: Image, position: Int) {
			binding.image = value
			binding.transformation  = transformation
			ViewCompat.setTransitionName(binding.root, "file_$position")
			binding.root.setOnClickListener {
				it.findNavController().navigate(
					FileFragmentDirections.actionToImageNavigation().actionId,
					ImageFragmentArgs(currentList.toTypedArray(), position, it.transitionName)
						.toBundle(),
					null,
					FragmentNavigatorExtras(it to it.transitionName)
				)
			}
			binding.executePendingBindings()
		}
	}
}
