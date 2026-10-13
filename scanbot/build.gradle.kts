/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ionos.scanbot"
    compileSdk = 36

    defaultConfig {
        minSdk = 28

        val scanbotLicenseKey = project.findProperty("ionosHidriveNextScanbotLicenseKey") as String? ?: ""
        val scanbotLicenseKeyUrl = project.findProperty("ionosHidriveNextScanbotLicenseKeyUrl") as String? ?: ""

        println("======================== Scanbot parameters ========================")
        println("scanbotLicenseKey: $scanbotLicenseKey")
        println("scanbotLicenseKeyUrl: $scanbotLicenseKeyUrl")
        println("====================================================================")

        buildConfigField("String", "SCANBOT_LICENSE_KEY", "\"$scanbotLicenseKey\"")
        buildConfigField("String", "SCANBOT_LICENSE_KEY_URL", "\"$scanbotLicenseKeyUrl\"")
        buildConfigField("Boolean", "IS_SCANBOT_FEATURE_AVAILABLE", "true")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    lint {
        abortOnError = false
    }
}

kotlin {
    jvmToolchain(21)
}

repositories {
    // maven {
    //     url = uri("https://nexus.scanbot.io/nexus/content/repositories/releases/")
    // }
}

dependencies {
    val scanbotSdkVersion = "6.2.1"
    implementation("io.scanbot:sdk-package-2:$scanbotSdkVersion")
    implementation("io.scanbot:sdk-common-ocr-assets:$scanbotSdkVersion")

    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation(libs.appcompat)
    implementation(libs.exifinterface)
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation(libs.material)

    implementation(libs.dagger)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.processor)
}