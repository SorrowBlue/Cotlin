android {
	defaultConfig {
		applicationId = "com.sorrowblue.cotlin"
		versionCode = 14
		versionName = "1.0.0"
	}
	signingConfigs {
		release {
			storeFile = file("M:/sorro/Documents/keystore.jks")
			storePassword = "storeyuukiasuna2s2"
			keyAlias = "SorrowBlueApp"
			keyPassword = "appyuukiasuna2s2"
		}

	}
	buildTypes {
		debug {
			applicationIdSuffix = ".debug"
			versionNameSuffix = "_debug"
		}
		release {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
			signingConfig = signingConfigs.getByName(name)
		}
	}
	buildFeatures {
		dataBinding = true
		viewBinding = true
	}
}

dependencies {
	implementation(Libs.appcompat)
	implementation(Libs.`legacy-support-v4`)
	implementation(project(Modules.ui))
	implementation(project(Modules.modules))
	implementation(project(Modules.`futures-folder`))
	implementation(project(Modules.`futures-settings`))
	implementation(project(Modules.`futures-permission`))
}
