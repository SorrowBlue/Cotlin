package com.sorrowblue.cotlin.list

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun listModule() = module {
	single { FolderListAdapter(androidContext()) }
	viewModel { FolderListViewModel(androidContext(), get()) }
}
