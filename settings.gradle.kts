enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

        google()
    }

    val group = "io.github.merseyside"
    val catalogVersions = "1.8.5"
    versionCatalogs {
        val multiplatformLibs by creating {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
        }

        val catalogPlugins by creating {
            from("$group:catalog-version-plugins:$catalogVersions")
        }
    }
}

include(":app")
include(":shared")
include(":chipsLib")

rootProject.name = "PartyApp"