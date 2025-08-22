// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sonarqube)
}

sonarqube {
    properties {
        property("sonar.projectKey", "itachi1706_AndroidABP")
        property("sonar.organization", "itachi1706")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.androidLint.reportPaths", "attribouter/build/reports/lint-results-debug.xml")
        property("sonar.projectVersion", project(":attribouter").ext.get("version") ?: "1.0")
    }
}