android {
	buildFeatures {
		dataBinding = true
		viewBinding = true
	}
}
dependencies {
	implementation(project(Modules.ui))
	implementation("com.davemorrissey.labs:subsampling-scale-image-view:3.10.0")
	implementation("androidx.exifinterface:exifinterface:1.3.0-alpha01")
}
