/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.20-M1"
    kotlin("plugin.serialization") version "1.4.20-M1"
}

repositories {
    jcenter()
    google()

    maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
    maven { url = uri("https://maven.fabric.io/public") }

}

val multiplatform = "0.8.0"
val kotlin = "1.4.20-M1"
val gradle = "4.0.1"
val butterknife = "10.2.1"
val maven_version = "2.1"
val google_services = "4.3.3"
val fabric = "1.31.2"
val sqldelight = "1.4.4"

dependencies {
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("com.github.dcendents:android-maven-gradle-plugin:$maven_version")
    implementation("com.squareup.sqldelight:gradle-plugin:$sqldelight")
    implementation("com.jakewharton:butterknife-gradle-plugin:$butterknife")
    implementation("com.google.gms:google-services:$google_services")
    implementation("io.fabric.tools:gradle:$fabric")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
