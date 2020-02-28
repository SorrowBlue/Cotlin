package com.sorrowblue.cotlin.list

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class Image(val uri: Uri, val name: String, val size: Int)
