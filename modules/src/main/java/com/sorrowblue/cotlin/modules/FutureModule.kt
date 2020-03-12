package com.sorrowblue.cotlin.modules

import com.sorrowblue.cotlin.futures.file.futureFileModule
import com.sorrowblue.cotlin.futures.folder.futureFolderModule

fun futureModule() = listOf(
	futureFolderModule(),
	futureFileModule()
)