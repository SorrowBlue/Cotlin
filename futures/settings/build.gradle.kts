android {
	resourcePrefix("settings_")
}

dependencies {
	implementation(Libs.`preference-ktx`)
	implementation(project(Modules.ui))
}
