pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/")}
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    }
}

rootProject.name = "Android ABP"
include(":attribouter", ":app")