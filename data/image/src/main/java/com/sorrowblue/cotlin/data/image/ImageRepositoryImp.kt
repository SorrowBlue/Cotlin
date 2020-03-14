package com.sorrowblue.cotlin.data.image

import android.content.Context
import androidx.exifinterface.media.ExifInterface
import com.sorrowblue.cotlin.domains.image.DetailImageInfo
import com.sorrowblue.cotlin.domains.image.Image
import com.sorrowblue.cotlin.domains.image.ImageRepository

internal class ImageRepositoryImp(private val context: Context) : ImageRepository {


	override fun getExif(image: Image): DetailImageInfo? {
		return context.contentResolver.openInputStream(image.uri)?.use {
			val exif = ExifInterface(it)
			exif.getAttribute(ExifInterface.TAG_SOFTWARE)
			DetailImageInfo(
				image = image,
				apertureValue = exif.getAttributeDouble(ExifInterface.TAG_APERTURE_VALUE, 0.0)
					.convertF(),
				exposureTime = exif.getAttributeDouble(ExifInterface.TAG_EXPOSURE_TIME, 0.0),
				model = exif.getAttribute(ExifInterface.TAG_MODEL),
				make = exif.getAttribute(ExifInterface.TAG_MAKE),
				iso = exif.getAttributeDouble(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, 0.0),
				orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0),
				flash = exif.getAttributeInt(ExifInterface.TAG_FLASH, 0)
			)
		}
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
