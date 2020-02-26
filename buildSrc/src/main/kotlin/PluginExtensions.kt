@file:Suppress("ObjectPropertyName", "unused", "FunctionName")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`android-application`: PluginDependencySpec
	get() = id("com.android.application")
val PluginDependenciesSpec.library: PluginDependencySpec get() = id("com.android.library")
val PluginAware.`android-library` get() = apply(plugin = "com.android.library")
val PluginAware.`android-application` get() = apply(plugin = "com.android.application")
val PluginAware.`oss-licenses-plugin` get() = apply(plugin = "com.google.android.gms.oss-licenses-plugin")
val PluginAware.`kotlinx-serialization` get() = apply(plugin = "kotlinx-serialization")
val PluginAware.`kotlin-android-extensions` get() = apply(plugin = "kotlin-android-extensions")
val PluginAware.`kotlin-android` get() = apply(plugin = "kotlin-android")
val PluginAware.`kotlin-kapt` get() = apply(plugin = "kotlin-kapt")


val DependencyHandler.`android-tools-build-gradle` get() = "com.android.tools.build:gradle:3.6.0"

fun DependencyHandler.`google-services`(version: String) = "com.google.gms:google-services:$version"
val DependencyHandler.`navigation-safe-args-gradle-plugin`
	get() =
		"androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

val DependencyHandler.`oss-licenses-plugin` get() = "com.google.android.gms:oss-licenses-plugin:0.10.1"

val PluginDependenciesSpec.`ben-manes-versions` get() = id("com.github.ben-manes.versions") version "0.28.0"

