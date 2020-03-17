package com.sorrowblue.cotlin.domains.image

import android.content.IntentSender

interface ImageRepository {
	fun getExif(image: Image): DetailImageInfo?
	suspend fun delete(image: Image, recover: (IntentSender) -> Unit)
}