package com.sorrowblue.cotlin.futures.folder

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun futureFolderModule() = module {
	single { FolderAdapter(androidContext()) }
	factory {
		FolderViewModel(
			get(),
			androidContext(),
			get()
		)
	}
}