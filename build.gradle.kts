/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

allprojects {
//    buildscript {
//        repositories {
//            mavenLocal()
//
//            jcenter()
//            google()
//
//            maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
//            maven { url = uri("https://kotlin.bintray.com/kotlinx") }
//            maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
//        }
//    }

    repositories {
        mavenLocal()

        google()
        jcenter()

        maven { url = uri("https://kotlin.bintray.com/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://dl.bintray.com/aakira/maven") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://raw.githubusercontent.com/guardianproject/gpmaven/master") }
        maven { url = uri("https://dl.bintray.com/kodein-framework/Kodein-DI") }
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
        maven { url = uri("https://dl.bintray.com/florent37/maven") }
        maven { url = uri("https://merseysoftware.bintray.com/mersey-library1")}
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}