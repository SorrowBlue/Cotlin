package com.sorrowblue.cotlin.domains.image

data class DetailImageInfo(
	val image: Image,
	val height: Long = 0,
	val width: Long = 0,
	val model: String?,
	val exposureTime: Double,
	val apertureValue: Double,
	val iso: Double,
	val flash: Int,
	val orientation: Int,
	val make: String?
)