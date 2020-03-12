android {
	resourcePrefix("image_")
	buildFeatures.dataBinding = true
}

dependencies {
	implementation(project(Modules.ui))
	implementation(project(Modules.`domain-image`))
}
