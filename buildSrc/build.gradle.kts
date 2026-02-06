plugins {
    `kotlin-dsl`
    kotlin("jvm") version catalogGradle.versions.kotlin.get()
}

dependencies {
    with(catalogGradle) {
        implementation(kotlin.gradle)
        implementation(android.gradle)
        implementation(kotlin.serialization)
        implementation(mersey.gradlePlugins)
        implementation(google.services)
        implementation(crashlytics)
    }
}
