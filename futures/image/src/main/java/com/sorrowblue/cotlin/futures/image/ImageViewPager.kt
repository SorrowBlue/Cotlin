package com.sorrowblue.cotlin.futures.image

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.github.chrisbanes.photoview.PhotoView
import com.sorrowblue.cotlin.domains.image.Image
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.futures.image.databinding.ImageViewPagerItemMainBinding as ItemBinding

internal class ImagePagerAdapter :
	DataBindAdapter<Image, ItemBinding, ImagePagerAdapter.ViewHolder>() {

	inner class ViewHolder(parent: ViewGroup) :
		DataBindAdapter.ViewHolder<Image, ItemBinding>(
			parent,
			R.layout.image_view_pager_item_main
		) {
		override fun bind(value: Image, position: Int) {
			binding.image = value
			binding.imageView.setOnClickListener {
				onClick.invoke()
			}
			binding.executePendingBindings()
		}
	}

	lateinit var onClick: () -> Unit
	override fun areItemsTheSame(old: Image, new: Image) = old.name == new.name
	override fun areContentsTheSame(old: Image, new: Image) = old == new
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
}

@BindingAdapter("rotationTo")
fun PhotoView.setRotationToForBinding(rotation: Float?) {
	setRotationTo(rotation ?: 0f)
}
