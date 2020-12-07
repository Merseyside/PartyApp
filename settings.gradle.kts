enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    repositories {
        jcenter()
        google()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://jetbrains.bintray.com/kotlin-native-dependencies") }
        maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

include(":app")
include(":shared")
include(":chipsLib")

private val isLocalDependencies = true

if (isLocalDependencies) {
    include(":kmp-clean-mvvm-arch")
    project(":kmp-clean-mvvm-arch").projectDir =
        File(rootDir.parent, "mersey-android-library/kmp-clean-mvvm-arch")

    include(":kmp-utils")
    project(":kmp-utils").projectDir =
        File(rootDir.parent, "mersey-android-library/kmp-utils")

    include(":utils")
    project(":utils").projectDir =
        File(rootDir.parent, "mersey-android-library/utils")

    include(":animators")
    project(":animators").projectDir =
        File(rootDir.parent, "mersey-android-library/animators")

    include(":archy")
    project(":archy").projectDir =
        File(rootDir.parent, "mersey-android-library/archy")

    include(":adapters")
    project(":adapters").projectDir =
        File(rootDir.parent, "mersey-android-library/adapters")
}

rootProject.name = "PartyApp"