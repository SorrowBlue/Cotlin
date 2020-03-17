package com.sorrowblue.cotlin.domains.folder

import com.sorrowblue.cotlin.domains.image.Image

interface FolderRepository {
	suspend fun getAll(): List<Folder>
	fun reload(folder: Folder): List<Image>
}