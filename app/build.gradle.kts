plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = 29
    namespace = "com.itachi1706.abp"

    defaultConfig {
        applicationId = "me.jfenn.attriboutersample"
        minSdk = 16
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        val githubToken = System.getenv("GITHUB_TOKEN_PUB")
        if (githubToken != null) {
            buildConfigField("String", "GITHUB_TOKEN", "\"$githubToken\"")
        } else {
            buildConfigField("String", "GITHUB_TOKEN", "null")
        }


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.multidex)

    implementation(project(":attribouter"))
}
