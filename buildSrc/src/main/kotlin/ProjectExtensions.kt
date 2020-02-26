import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.project(configure: TestedExtension.() -> Unit) {
	if (hasProperty("android")) {
		configure.invoke(extensions.getByType())
	}
}

fun Project.library(action: LibraryExtension.() -> Unit) {
	if (hasProperty("android") && project.name != "app") {
		action(extensions.getByName("android") as LibraryExtension)
	}
}

fun Project.app(action: BaseAppModuleExtension.() -> Unit) {
	if (hasProperty("android") && project.name == "app") {
		action(extensions.getByName("android") as BaseAppModuleExtension)
	}
}


fun <T> NamedDomainObjectContainer<T>.release(configureAction: T.() -> Unit) {
	kotlin.runCatching {
		getByName("release") { configureAction(this) }
	}.onFailure {
		create("release") { configureAction(this) }
	}
}


fun <T> NamedDomainObjectContainer<T>.debug(configureAction: T.() -> Unit) {
	getByName("debug") { configureAction(this) }
}

@Suppress("UnstableApiUsage")
fun TestedExtension.kotlinOptions(action: KotlinJvmOptions.() -> Unit) =
	(this as ExtensionAware).extensions.configure("kotlinOptions", action)


fun Project.kotlinCompile(action: KotlinCompile.() -> Unit) =
	tasks.withType<KotlinCompile>().all { action(this) }

const val KOTLIN_INLINE_CLASSES = "-Xinline-classes"
const val KOTLIN_USE_EXPERIMENTAL = "-Xuse-experimental=kotlin.Experimental"


fun DependencyHandler.testImplementation(dependencyNotation: Any) =
	add("testImplementation", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: Any) =
	add("implementation", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: Any) =
	add("api", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) =
	add("androidTestImplementation", dependencyNotation)
