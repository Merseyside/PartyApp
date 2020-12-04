plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("io.fabric")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
        versionCode = Versions.Android.versionCode
        versionName = Versions.Android.version

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = true
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures.dataBinding = true

    sourceSets.getByName("main") {

        res.srcDir("src/main/res/")
        res.srcDir("src/main/res/layouts/fragments")
        res.srcDir("src/main/res/layouts/activity")
        res.srcDir("src/main/res/layouts/dialog")
        res.srcDir("src/main/res/layouts/views")
        res.srcDir("src/main/res/value/values-light")
        res.srcDir("src/main/res/value/values-night")
    }
}

val androidLibs = listOf(
    Deps.Libs.Android.kotlinStdLib.name,
    Deps.Libs.Android.recyclerView.name,
    Deps.Libs.MultiPlatform.serialization.android!!,
    Deps.Libs.Android.coroutines.name,
    Deps.Libs.Android.coroutinesCore.name,
    Deps.Libs.Android.constraintLayout.name,
    Deps.Libs.Android.appCompat.name,
    Deps.Libs.Android.material.name,
    Deps.Libs.Android.lifecycle.name,
    Deps.Libs.Android.cardView.name,
    Deps.Libs.Android.annotation.name,
    Deps.Libs.Android.dagger.name,
    Deps.Libs.MultiPlatform.sqlDelight.android!!,
    Deps.Libs.Android.gson.name,
    Deps.Libs.Android.horizontalSelector.name,
    Deps.Libs.Android.playServicesAds.name,
    Deps.Libs.Android.firebaseFirestore.name,
    Deps.Libs.Android.firebaseAnalytics.name,
    Deps.Libs.Android.crashlytics.name,
    Deps.Libs.Android.circleImage.name,
    Deps.Libs.Android.cicerone.name
)

val merseyLibs = listOf(
    Deps.Libs.Android.MerseyLibs.archy.name,
    Deps.Libs.Android.MerseyLibs.adapters.name,
    Deps.Libs.Android.MerseyLibs.animators.name,
    Deps.Libs.Android.MerseyLibs.utils.name
)

val modulez = listOf(
    ":shared",
    ":chipsLib",
    LibraryModules.Android.archy,
    LibraryModules.Android.adapters,
    LibraryModules.Android.animators,
    LibraryModules.Android.utils
)

dependencies {
    if (isLocalDependencies()) {
        modulez.forEach { module -> implementation(project(module)) }
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }

    androidLibs.forEach { lib -> implementation(lib)}

    kaptLibrary(Deps.Libs.Android.daggerCompiler)

    compileOnly("javax.annotation:jsr250-api:1.0")
}

apply("plugin" to "com.google.gms.google-services")