plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform")
    id("maven-publish")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/*.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val mppLibs = listOf(
    Deps.Libs.MultiPlatform.kotlinStdLib,
    Deps.Libs.MultiPlatform.coroutines,
    Deps.Libs.MultiPlatform.serialization,
    Deps.Libs.MultiPlatform.kodein,
    Deps.Libs.MultiPlatform.sqlDelight,
    Deps.Libs.MultiPlatform.preferences
)

val merseyModules = listOf(
    LibraryModules.MultiPlatform.utils
)

val merseyLibs = listOf(
    Deps.Libs.MultiPlatform.MerseyLibs.cleanMvvmArch,
    Deps.Libs.MultiPlatform.MerseyLibs.utils
)

dependencies {
    mppLibs.forEach { mppLibrary(it) }

    if (isLocalDependencies()) {
        merseyModules.forEach { module -> mppModule(module) }
    } else {
        merseyLibs.forEach { lib -> mppLibrary(lib) }
    }

    kaptLibrary(Deps.Libs.Android.daggerCompiler)
    compileOnly("javax.annotation:jsr250-api:1.0")
}

sqldelight {
    database("CalcDatabase") {
        packageName = "com.merseyside.partyapp.data.db"
        sourceFolders = listOf("sqldelight")
        schemaOutputDirectory = file("build/dbs")
        //dependency(project(":OtherProject"))
    }
    linkSqlite = false
}