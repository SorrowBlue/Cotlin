package com.sorrowblue.cotlin.modules

import com.sorrowblue.cotlin.data.folder.dataFolderModule
import com.sorrowblue.cotlin.data.image.dataImage

fun dataModule() = listOf(
	dataImage(),
	dataFolderModule()
)