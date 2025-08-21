import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

ext.set("version", "0.1.9")
ext.set("versionCode", 18)

android {
    compileSdk = 29
    namespace = "com.itachi1706.abp"

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions.unitTests.apply {
        isIncludeAndroidResources = true
        tasks.withType<Test>().all(KotlinClosure1<Test, Test>({
            apply {
                testLogging.events = setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.FAILED,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_OUT
                )
            }
        }, this))
    }
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.material)
    implementation(libs.flexbox)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.androidutils)
    implementation(libs.gitrest) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-runtime-common")
    }
}
