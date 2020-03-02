android {
	buildFeatures {
		dataBinding = true
		viewBinding = true
	}

}
dependencies {
	api(Libs.material)
	api(Libs.transition)
	api(Libs.constraintlayout)
	api(Libs.`navigation-fragment-ktx`)
	api(Libs.`navigation-ui-ktx`)
	api(Libs.`lifecycle-livedata-ktx`)
	api(Libs.`lifecycle-viewmodel-ktx`)
	api(Libs.coil)
	api(Libs.`coil-svg`)
	api(Libs.`koin-android`)
	api(Libs.`koin-androidx-viewmodel`)
	api(Libs.`fragment-ktx`)
	api(Libs.`kotlinx-coroutines-android`)
	implementation(Libs.`lifecycle-process`)
}
