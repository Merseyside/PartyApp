object Versions {

    object Android {
        const val compileSdk = 28
        const val targetSdk = 29
        const val minSdk = 21

        const val version = "1.14"
        const val versionCode = 114
    }

    const val kotlin = "1.3.72"

    private const val mokoResources = "0.9.0"

    object Plugins {
        const val kotlin = Versions.kotlin
        const val serialization = Versions.kotlin
        const val androidExtensions = Versions.kotlin
        const val mokoResources = Versions.mokoResources
    }

    object Libs {

        object MerseyLibs {
            const val version = "1.2.4"
        }

        object Android {
            const val kotlinStdLib = kotlin
            const val coroutines = "1.3.7"
            const val appCompat = "1.1.0"
            const val material = "1.2.0-beta01"
            const val fragment = "1.2.4"
            const val constraintLayout = "1.1.3"
            const val lifecycle = "2.0.0"
            const val cardView = "1.0.0"
            const val recyclerView = "1.0.0"
            const val dagger = "2.27"
            const val navigation = "2.2.1"
            const val paging = "1.0.1"
            const val billing = "2.2.0"
            const val publisher = "v3-rev142-1.25.0"
            const val auth = "0.20.0"
            const val firebaseFirestore = "21.4.3"
            const val firebase = "17.4.2"
            const val playCore = "1.7.2"
            const val keyboard = "2.3.0"
            const val gson = "2.8.6"
            const val worker = "2.3.4"
            const val room = "2.0.0"
            const val horizontalSelector = "1.13"
            const val cicerone = "5.0.0"
            const val playServicesAds = "18.3.0"
            const val crashlytics = "2.10.1"
            const val circleImage = "3.0.1"
        }

        object MultiPlatform {
            const val kotlinStdLib = kotlin

            const val coroutines = "1.3.5"
            const val serialization = "0.20.0"
            const val mokoMvvm = "0.6.0"
            const val mokoResources = Versions.mokoResources

            const val kodein = "6.5.5"
            const val sqlDelight = "1.3.0"
            const val preferences = "1.0.0"
        }
    }
}