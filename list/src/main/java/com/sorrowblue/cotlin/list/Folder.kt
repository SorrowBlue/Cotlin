package com.sorrowblue.cotlin.list

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Folder(val name: String, val child: MutableList<Image>) : Serializable {
	constructor(name: String, vararg child: Image) : this(name, child.toMutableList())
}
