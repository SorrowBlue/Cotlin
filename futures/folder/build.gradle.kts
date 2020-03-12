android {
	resourcePrefix("folder_")
	buildFeatures.dataBinding = true
}

dependencies {
	implementation(Libs.swiperefreshlayout)
	implementation(project(Modules.ui))
	implementation(project(Modules.`domain-folder`))
	implementation(project(Modules.`futures-file`))
}
