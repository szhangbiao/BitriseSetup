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
                arguments["room.incremental"] = "true"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    // Some libs (such as androidx.core:core-ktx 1.2.0 and newer) require Java 8
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // To avoid the compile error: "Cannot inline bytecode built with JVM target 1.8
    // into bytecode that is being built with JVM target 1.6"
    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }

    //使用robolectric必须配置
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(platform(project(":depPom")))
    implementation("androidx.test:core-ktx:1.4.0")
    kapt(platform(project(":depPom")))
    api(project(":core:base"))

    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    kapt(Libs.ROOM_COMPILER)
    implementation(Libs.CRYPTO)

    // UI
    implementation(Libs.APPCOMPAT)
    implementation(Libs.MATERIAL)
    implementation(Libs.CORE_KTX)

    // Local unit tests
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.TEST_CORE_KTX)
    testImplementation(Libs.ROOM_KTX)
    testImplementation(Libs.ROOM_RUNTIME)
    testImplementation(Libs.ROBOLECTRIC)

    // Instrumentation tests
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.ESPRESSO_CORE)
}