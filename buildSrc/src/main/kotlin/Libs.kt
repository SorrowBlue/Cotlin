import Versions.coroutines
import Versions.koin
import Versions.kotlin
import Versions.lifecycle
import Versions.navigation

private fun kotlin(module: String, version: String) = "org.jetbrains.kotlin:kotlin-$module:$version"
private fun kotlinx(module: String, version: String) =
	"org.jetbrains.kotlinx:kotlinx-$module:$version"

@Suppress("ObjectPropertyName")
object Libs {
	val `kotlin-stdlib-jdk7` = kotlin("stdlib-jdk7", kotlin)

	/**
	 * @link https://github.com/Kotlin/kotlinx.coroutines
	 */
	val `kotlinx-coroutines-android` = kotlinx("coroutines-android", coroutines)

	const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha01"
	const val `core-ktx` = "androidx.core:core-ktx:1.5.0-alpha01"
	const val `legacy-support-v4` = "androidx.legacy:legacy-support-v4:1.0.0"
	const val `fragment-ktx` = "androidx.fragment:fragment-ktx:1.3.0-alpha06"
	const val `activity-ktx` = "androidx.activity:activity-ktx:1.2.0-alpha06"

	const val material = "com.google.android.material:material:1.3.0-alpha01"
	const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta6"
	const val transition = "androidx.transition:transition:1.3.1"
	const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-rc01"
	const val `preference-ktx` = "androidx.preference:preference-ktx:1.1.1"

	const val coil = "io.coil-kt:coil:${Versions.coil}"
	const val `coil-svg` = "io.coil-kt:coil-svg:${Versions.coil}"

	const val `koin-android` = "org.koin:koin-android:$koin"
	const val `koin-androidx-viewmodel` = "org.koin:koin-androidx-viewmodel:$koin"

	const val photoView = "com.github.chrisbanes:PhotoView:2.3.0"
	const val `subsampling-scale-image-view` =
		"com.davemorrissey.labs:subsampling-scale-image-view:3.10.0"

	/**
	 * @link https://developer.android.com/jetpack/androidx/releases/navigation
	 */
	const val `navigation-ui-ktx` = "androidx.navigation:navigation-ui-ktx:$navigation"
	const val `navigation-fragment-ktx` = "androidx.navigation:navigation-fragment-ktx:$navigation"

	/**
	 * @link https://developer.android.com/jetpack/androidx/releases/lifecycle
	 */
	const val `lifecycle-livedata-ktx` = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle"
	const val `lifecycle-viewmodel-ktx` = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle"
	const val `lifecycle-process` = "androidx.lifecycle:lifecycle-process:$lifecycle"

	const val junit = "junit:junit:4.13"
	const val `androidx-junit` = "androidx.test.ext:junit:1.1.2-rc01"
	const val `androidx-junit-ktx` = "androidx.test.ext:junit-ktx:1.1.2-rc01"
	const val `espresso-core` = "androidx.test.espresso:espresso-core:3.3.0-rc01"
}
