package com.sorrowblue.cotlin.domains.image

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Keep
@Parcelize
data class Image(
	val uri: Uri,
	val name: String,
	val contentType: String,
	val size: Long,
	val dateAdded: LocalDateTime,
	val dateModified: LocalDateTime,
	val rotate: Float = 0f
) : Parcelable

