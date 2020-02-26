android {
	defaultConfig {
		applicationId = "com.sorrowblue.cotlin"
		versionCode = 8
		versionName = "1.0.0"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	dataBinding.isEnabled = true
	viewBinding.isEnabled = true
}

dependencies {
	implementation(Libs.appcompat)
	implementation(Libs.`legacy-support-v4`)
	implementation(project(Modules.ui))
	implementation(project(Modules.list))
}
