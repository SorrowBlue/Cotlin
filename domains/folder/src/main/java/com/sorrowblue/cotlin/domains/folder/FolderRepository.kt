package com.sorrowblue.cotlin.domains.folder

import androidx.annotation.RequiresApi
import com.sorrowblue.cotlin.domains.image.Image

interface FolderRepository {
	@RequiresApi(value = 26)
	suspend fun getAll(): List<Folder>
	@RequiresApi(value = 26)
	fun reload(folder: Folder): List<Image>
}