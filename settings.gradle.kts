pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Real Estate Listing"
include(":app")

includeDirectory("features")
includeDirectory("utils")


fun includeDirectory(name: String) {
    file(name).listFiles()?.forEach { subDir ->
        if (file("$subDir/build.gradle.kts").exists()) {
            include(subDir.name)
            project(":${subDir.name}").projectDir = subDir
        }
    }
}
