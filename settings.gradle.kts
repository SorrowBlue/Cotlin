val excludeFolder = setOf("build", "src", "schemas", "libs")
rootProject.name = "Cotlin"
include(":app", ":modules", ":ui", ":list", ":view")
listOf("futures", "domains", "data").forEach { libRoot ->
	file("/$libRoot").listFiles()?.forEach { baseModule ->
		include(":$libRoot:${baseModule.name}")
		baseModule.listFiles()
			?.filter { it.isDirectory && !excludeFolder.contains(it.name) }
			?.forEach { submodule ->
				include(":$libRoot:${baseModule.name}:${submodule.name}")
			}
	}
}
