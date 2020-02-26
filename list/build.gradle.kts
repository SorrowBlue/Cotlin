android {
	buildFeatures {
		dataBinding = true
		viewBinding = true
	}
}
dependencies {
	implementation(project(Modules.ui))
	implementation(Libs.swiperefreshlayout)
}
