android {
	buildFeatures {
		dataBinding = true
		viewBinding = true
	}
}
dependencies {
	implementation(project(Modules.ui))
	implementation(Libs.swiperefreshlayout)
	implementation("com.github.chrisbanes:PhotoView:2.0.0")
}
