package com.sorrowblue.cotlin.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun uiModule() = module {
	viewModel { UiViewModel() }
}