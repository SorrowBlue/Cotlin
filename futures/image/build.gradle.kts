android {
	resourcePrefix("image_")
	buildFeatures {
		dataBinding = true
	}
}

dependencies {
	implementation(Libs.photoView)
	implementation(Libs.`subsampling-scale-image-view`)
	implementation(project(Modules.ui))
	implementation(project(Modules.`domain-folder`))
}
