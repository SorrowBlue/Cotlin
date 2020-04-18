package com.sorrowblue.cotlin.futures.image

import com.sorrowblue.cotlin.domains.folder.Folder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun futureImageModule() = module {
	factory { ImagePagerAdapter() }
	viewModel { (folder: Folder) ->
		ImageViewModel(
			repo = get(),
			context = androidContext(),
			folder = folder,
			adapter = get()
		)
	}
}