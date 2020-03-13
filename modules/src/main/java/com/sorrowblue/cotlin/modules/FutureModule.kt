package com.sorrowblue.cotlin.modules

import com.sorrowblue.cotlin.futures.file.futureFileModule
import com.sorrowblue.cotlin.futures.folder.futureFolderModule
import com.sorrowblue.cotlin.futures.permission.futurePermissionModule
import com.sorrowblue.cotlin.ui.uiModule

fun futureModule() = listOf(
	uiModule(),
	futureFolderModule(),
	futureFileModule(),
	futurePermissionModule()
)