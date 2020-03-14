package com.sorrowblue.cotlin

import android.app.Application
import com.sorrowblue.cotlin.futures.settings.Settings
import com.sorrowblue.cotlin.modules.dataModule
import com.sorrowblue.cotlin.modules.futureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class CotlinApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@CotlinApplication)
			modules(dataModule() + futureModule())
		}
		Settings.applyDarkMode(applicationContext)
	}
}
