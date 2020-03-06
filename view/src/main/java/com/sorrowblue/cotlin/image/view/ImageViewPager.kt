package com.sorrowblue.cotlin.image.view

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.davemorrissey.labs.subscaleview.ImageSource
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.image.view.databinding.ImageViewPagerItemMainBinding as ItemBinding

internal class ImagePagerAdapter :
	DataBindAdapter<Image, ItemBinding, ImagePagerAdapter.ViewHolder>() {

	inner class ViewHolder(parent: ViewGroup) : DataBindAdapter.ViewHolder<Image, ItemBinding>(
		parent, R.layout.image_view_pager_item_main
	) {
		override fun bind(value: Image, position: Int) {
			binding.imageView.setImage(ImageSource.uri(value.uri))
			ViewCompat.setTransitionName(binding.imageView, value.name)
			binding.imageView.setOnClickListener {
				onClick.invoke()
			}
		}
	}

	lateinit var onClick: () -> Unit

	var rot: Pair<Int, Int>? = null

	fun setList(list: List<Image>) {
		currentList = list
	}

	override fun areItemsTheSame(old: Image, new: Image) = old.name == new.name

	override fun areContentsTheSame(old: Image, new: Image) = old == new

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
	fun setRotation(pos: Int, r: Int) {
		rot = pos to r
		notifyItemChanged(pos)
	}
}