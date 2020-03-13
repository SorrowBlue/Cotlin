package com.sorrowblue.cotlin.futures.permission

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun futurePermissionModule() = module {
	viewModel { PermissionViewModel() }
}