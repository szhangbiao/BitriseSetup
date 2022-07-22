plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = AppConfig.kotlinJvmTarget
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(platform(project(":depPom")))
    kapt(platform(project(":depPom")))
    api(project(":core:base"))

    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    kapt(Libs.ROOM_COMPILER)
    implementation(Libs.CRYPTO)
    implementation(Libs.JODA_ANDROID)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.COROUTINES_ANDROID)

    // UI
    implementation(Libs.APPCOMPAT)
    implementation(Libs.MATERIAL)
    implementation(Libs.CORE_KTX)

    // Local unit tests
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.JUPITER)
    testImplementation(Libs.JUPITER_ENGINE)
    testImplementation(Libs.TEST_CORE_KTX)
    testImplementation(Libs.ROOM_TESTING)
    testImplementation(Libs.ROBOLECTRIC)
    testImplementation(Libs.JODA_TIME)
    testImplementation(Libs.COROUTINES_TEST)

    // Instrumentation tests
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.ESPRESSO_CORE)
}