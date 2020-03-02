package com.sorrowblue.cotlin.list

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import coil.api.load
import com.sorrowblue.cotlin.list.databinding.ImageViewPagerItemMainBinding
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter

internal class ImagePagerAdapter :
	DataBindAdapter<Image, ImageViewPagerItemMainBinding, ImagePagerAdapter.ViewHolder>() {

	inner class ViewHolder(parent: ViewGroup) :
		DataBindAdapter.ViewHolder<Image, ImageViewPagerItemMainBinding>(
			parent,
			R.layout.image_view_pager_item_main
		) {
		override fun bind(value: Image, position: Int) {
			binding.imageView.load(value.uri) {
				if (rot?.first == position) {
					this.listener { data, source ->
						binding.imageView.setRotationTo(rot!!.second.toFloat())
					}
				}
			}
			ViewCompat.setTransitionName(binding.imageView, value.name)
		}
	}

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