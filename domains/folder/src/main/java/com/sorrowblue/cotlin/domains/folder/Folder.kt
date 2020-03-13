package com.sorrowblue.cotlin.domains.folder

import android.os.Parcelable
import androidx.annotation.Keep
import com.sorrowblue.cotlin.domains.image.Image
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Folder(
	val name: String, val path: String, val child: MutableList<Image>
) : Parcelable
