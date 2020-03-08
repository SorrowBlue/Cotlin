android {
	resourcePrefix("permission_")
	buildFeatures.dataBinding = true
}

dependencies {
	implementation(project(Modules.ui))
}
