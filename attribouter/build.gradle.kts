import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

ext.set("version", "1.0.0")
ext.set("versionCode", 19)

android {
    compileSdk = 36
    namespace = "com.itachi1706.abp.attribouter"

    defaultConfig {
        minSdk = 21

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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(kotlin("test"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.http)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.material)
    implementation(libs.flexbox)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)
}

apply(from = "./publish.gradle")