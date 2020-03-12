package com.sorrowblue.cotlin.futures.file

import com.sorrowblue.cotlin.domains.folder.Folder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun futureFileModule() = module {
	factory { FileAdapter() }
	viewModel { (folder: Folder) ->  FileViewModel(androidContext(), folder, get(), get()) }
}