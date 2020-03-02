// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
	extra["kotlin_version"] = "1.3.61"
	repositories {
		google()
		jcenter()
	}
	dependencies {
		classpath(kotlin("gradle-plugin", Versions.kotlin))
		classpath(`android-tools-build-gradle`("4.0.0-beta01"))
		classpath(`oss-licenses-plugin`)
		classpath(`navigation-safe-args-gradle-plugin`)

		// NOTE: Do not place your application dependencies here; they belong
		// in the individual module build.gradle files
	}
}

allprojects {
	repositories {
		google()
		jcenter()
		maven(url = "https://jitpack.io")
	}
}
subprojects {
	afterEvaluate {
		project {
			buildToolsVersion = "29.0.3"
			compileSdkVersion(29)
			defaultConfig {
				minSdkVersion(23)
				targetSdkVersion(29)
				testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
			}
			compileOptions {
				sourceCompatibility = JavaVersion.VERSION_1_8
				targetCompatibility = JavaVersion.VERSION_1_8
			}
			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
		library {
			defaultConfig {
				consumerProguardFiles("consumer-rules.pro")
			}
			libraryVariants.all {
				generateBuildConfigProvider.configure {
					enabled = false
				}
			}
		}
	}
	if (project.name == "app") {
		`android-application`
		`oss-licenses-plugin`
	} else {
		`android-library`
	}
	`kotlin-android`
	`kotlin-android-extensions`
	`kotlin-kapt`
	apply(plugin = "androidx.navigation.safeargs.kotlin")

	dependencies {
		api(Libs.`kotlin-stdlib-jdk7`)
		implementation(Libs.`core-ktx`)
		testImplementation(Libs.junit)
		androidTestImplementation(Libs.`androidx-junit`)
		androidTestImplementation(Libs.`androidx-junit-ktx`)
		androidTestImplementation(Libs.`espresso-core`)
	}
}
plugins {
	`ben-manes-versions`
}

tasks.register("clean", Delete::class) { delete(rootProject.buildDir) }
