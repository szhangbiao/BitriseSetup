plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

val buildDateTime = if (project.hasProperty("devBuild")) AppConfig.fixedBuildNo else Versions.buildTime()

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = "com.szhangbiao.bitrisesetup"

        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = Versions.versionCode
        versionName = "${Versions.version}-$buildDateTime"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    if (project.hasProperty("devBuild")) {
        splits.abi.isEnable = false
        splits.density.isEnable = false
        aaptOptions.cruncherEnabled = false
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            // Crashlytics
//            ext.alwaysUpdateBuildId = false
            isCrunchPngs = false
        }
    }

    flavorDimensions.add(AppConfig.dimension)
    productFlavors {
        create("dev") {
            resourceConfigurations.addAll(listOf("en", "xxhdpi"))
            dimension = AppConfig.dimension
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = AppConfig.kotlinJvmTarget
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        quiet = true
        abortOnError = true
        ignoreWarnings = true
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/NOTICE.txt", "META-INF/LICENSE.txt"))
    }
}

dependencies {

    api(platform(project(":depPom")))
    kapt(platform(project(":depPom")))
    androidTestApi(platform(project(":depPom")))
    api(project(":core:feature"))

    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.CARDVIEW)
    implementation(Libs.CARDVIEW)
    implementation(Libs.BROWSER)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.DRAWER_LAYOUT)
    implementation(Libs.MATERIAL)
    implementation(Libs.FLEXBOX)
    implementation(Libs.LOTTIE)
    implementation(Libs.INK_PAGE_INDICATOR)
    implementation(Libs.SLIDING_PANE_LAYOUT)

    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.COROUTINES_ANDROID)

    // Architecture Components
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_RUNTIME_KTX)
    kapt(Libs.LIFECYCLE_COMPILER)
    testImplementation(Libs.ARCH_TESTING)
    implementation(Libs.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.NAVIGATION_UI_KTX)

    // Instrumentation tests
    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.RUNNER)
    androidTestImplementation(Libs.RULES)
    androidTestImplementation(Libs.FRAGMENT_TEST)
    debugImplementation(Libs.FRAGMENT_TEST)

    // Local unit tests
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.JUPITER_ENGINE)
    testImplementation(Libs.MOCKITO_CORE)
    testImplementation(Libs.MOCKITO_KOTLIN)
}