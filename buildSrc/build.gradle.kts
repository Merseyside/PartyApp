plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.20"
}

repositories {
    jcenter()
    google()

    maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
}

val multiplatform = "0.8.0"
val kotlin = "1.4.20"
val gradle = "4.1.1"
val butterknife = "10.2.1"
val maven_version = "2.1"
val google_services = "4.3.3"
val sqldelight = "1.4.4"
val resources = "0.13.1"

dependencies {
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("com.github.dcendents:android-maven-gradle-plugin:$maven_version")
    implementation("com.squareup.sqldelight:gradle-plugin:$sqldelight")
    implementation("com.jakewharton:butterknife-gradle-plugin:$butterknife")
    implementation("dev.icerock.moko:resources-generator:$resources")
    implementation("com.google.gms:google-services:$google_services")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
