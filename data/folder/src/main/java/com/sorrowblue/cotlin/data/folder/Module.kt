package com.sorrowblue.cotlin.data.folder

import com.sorrowblue.cotlin.domains.folder.FolderRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

fun dataFolderModule() = module {
	factory { FolderRepositoryImp(androidContext()) } bind FolderRepository::class
}