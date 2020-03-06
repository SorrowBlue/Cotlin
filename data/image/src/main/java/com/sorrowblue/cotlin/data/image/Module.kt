package com.sorrowblue.cotlin.data.image

import com.sorrowblue.cotlin.domains.image.ImageRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

fun dataImage() = module {
	single { ImageRepositoryImp(androidContext()) } bind ImageRepository::class
}