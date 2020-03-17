package com.sorrowblue.cotlin.futures.image

//import com.sorrowblue.cotlin.futures.image.databinding.ImageViewPagerItemMainBinding as ItemBinding
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.exifinterface.media.ExifInterface
import com.sorrowblue.cotlin.domains.image.Image
import com.sorrowblue.cotlin.ui.recyclerview.DataBindAdapter
import com.sorrowblue.cotlin.futures.image.databinding.ImageViewPagerItemPhotoBinding as ItemBinding

internal class ImagePagerAdapter :
	DataBindAdapter<Image, ItemBinding, ImagePagerAdapter.ViewHolder>() {

	inner class ViewHolder(parent: ViewGroup) :
		DataBindAdapter.ViewHolder<Image, ItemBinding>(
			parent, R.layout.image_view_pager_item_photo
		) {
		override fun bind(value: Image, position: Int) {
			binding.image = value
			ViewCompat.setTransitionName(binding.imageView, value.name)
			binding.root.context.contentResolver.openInputStream(value.uri)?.use {
				val exif = ExifInterface(it)
				Log.d(
					javaClass.simpleName, """
					Camera: ${exif.getAttribute(ExifInterface.TAG_MODEL)}
					TAG_EXPOSURE_TIME: ${exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)}
					f/${exif.getAttributeDouble(ExifInterface.TAG_APERTURE_VALUE, -1.0).convertF()}
					ISO: ${exif.getAttributeDouble(
						ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY,
						-1.0
					)}
					flash: ${exif.getAttributeDouble(ExifInterface.TAG_FLASH, -1.0)}
					orientation: ${exif.getAttribute(ExifInterface.TAG_ORIENTATION)}
					make: ${exif.getAttribute(ExifInterface.TAG_MAKE)}
				""".trimIndent()
				)
			}

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


fun Double.convertF() = when {
	this < 0.707 -> 0.5
	this < 1.0 -> 0.70
	this < 1.414 -> 1.0
	this < 2.0 -> 1.4
	this < 2.828 -> 2.0
	this < 4.0 -> 2.8
	this < 5.657 -> 4.0
	this < 8.0 -> 5.6
	this < 11.31 -> 8.0
	this < 16.0 -> 11.0
	this < 22.62 -> 16.0
	this < 32.0 -> 22.0
	this < 45.25 -> 32.0
	this < 64.0 -> 45.0
	this < 90.51 -> 64.0
	this < 128.0 -> 90.0
	this < 181.02 -> 128.0
	this < 256.0 -> 180.0
	else -> 256.0
}