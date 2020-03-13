import Versions.coroutines
import Versions.koin
import Versions.kotlin
import Versions.lifecycle
import Versions.navigation
import Versions.paging
import Versions.room
import Versions.work

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
	val `kotlinx-coroutines-play-services` = kotlinx("coroutines-play-services", coroutines)

	val `kotlin-serialization` = kotlin("serialization", kotlin)
	val `kotlinx-serialization-runtime` = kotlinx("serialization-runtime", "0.13.0")

	const val appcompat = "androidx.appcompat:appcompat:1.2.0-alpha03"
	const val `core-ktx` = "androidx.core:core-ktx:1.3.0-alpha02"
	const val `legacy-support-v4` = "androidx.legacy:legacy-support-v4:1.0.0"
	const val `fragment-ktx` = "androidx.fragment:fragment-ktx:1.3.0-alpha01"
	const val browser = "androidx.browser:browser:1.3.0-alpha01"

	const val `recyclerview-selection` = "androidx.recyclerview:recyclerview-selection:1.1.0-rc01"
	const val material = "com.google.android.material:material:1.2.0-alpha05"
	const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
	const val transition = "androidx.transition:transition:1.3.1"
	const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-beta01"
	const val `preference-ktx` = "androidx.preference:preference-ktx:1.1.0"
	const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

	const val `play-services-wallet` = "com.google.android.gms:play-services-wallet:18.0.0"

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

	/**
	 * @link https://developer.android.com/jetpack/androidx/releases/paging
	 */
	const val `paging-common-ktx` = "androidx.paging:paging-common-ktx:$paging"
	const val `paging-runtime-ktx` = "androidx.paging:paging-runtime-ktx:$paging"

	const val `work-runtime-ktx` = "androidx.work:work-runtime-ktx:$work"
	const val `work-testing` = "androidx.work:work-testing:$work"

	const val `firebase-analytics` = "com.google.firebase:firebase-analytics:17.2.2"
	const val `firebase-auth` = "com.google.firebase:firebase-auth:19.2.0"
	const val `firebase-ui-auth` = "com.firebaseui:firebase-ui-auth:6.2.0"
	const val `firebase-ui-firestore` = "com.firebaseui:firebase-ui-firestore:6.2.0"
	const val `firebase-firestore-ktx` = "com.google.firebase:firebase-firestore-ktx:21.4.0"
	const val `firebase-messaging` = "com.google.firebase:firebase-messaging:20.1.0"
	const val `firebase-storage-ktx` = "com.google.firebase:firebase-storage-ktx:19.1.1"

	const val `room-compiler` = "androidx.room:room-compiler:$room"
	const val `room-ktx` = "androidx.room:room-ktx:$room"


	const val junit = "junit:junit:4.13"
	const val `androidx-junit` = "androidx.test.ext:junit:1.1.2-alpha04"
	const val `androidx-junit-ktx` = "androidx.test.ext:junit-ktx:1.1.2-alpha04"
	const val `espresso-core` = "androidx.test.espresso:espresso-core:3.3.0-alpha04"
}
