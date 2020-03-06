package com.sorrowblue.cotlin.image.view

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Image(val uri: Uri, val name: String, val size: Int) : Parcelable
