package com.sorrowblue.cotlin.domains.image

interface ImageRepository {
	fun getExif(image: Image): DetailImageInfo?
}