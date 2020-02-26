package com.sorrowblue.cotlin.list

import java.io.Serializable

data class Folder(val name: String, val child: MutableList<Image>) : Serializable
