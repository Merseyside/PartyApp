object Deps {
    object Plugins {
        const val kotlinSerialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.Plugins.serialization}"
        const val androidExtensions =
            "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.Plugins.androidExtensions}"
        const val mokoResources =
            "dev.icerock.moko:resources-generator:${Versions.Plugins.mokoResources}"
    }

    object Libs {
        object Android {
            val kotlinStdLib = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Common.kotlinStdLib}"
            )
            val coroutinesCore = AndroidLibrary(
                name = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Common.coroutines}"
            )
            val coroutines = AndroidLibrary(
                name = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Common.coroutines}"
            )
            val appCompat = AndroidLibrary(
                name = "androidx.appcompat:appcompat:${Versions.Libs.Android.appCompat}"
            )
            val material = AndroidLibrary(
                name = "com.google.android.material:material:${Versions.Libs.Android.material}"
            )
            val fragment = AndroidLibrary(
                name = "androidx.fragment:fragment:${Versions.Libs.Android.fragment}"
            )
            val recyclerView = AndroidLibrary(
                name = "androidx.recyclerview:recyclerview:${Versions.Libs.Android.recyclerView}"
            )
            val constraintLayout = AndroidLibrary(
                name = "androidx.constraintlayout:constraintlayout:${Versions.Libs.Android.constraintLayout}"
            )
            val lifecycle = AndroidLibrary(
                name = "androidx.lifecycle:lifecycle-extensions:${Versions.Libs.Android.lifecycle}"
            )
            val cardView = AndroidLibrary(
                name = "androidx.cardview:cardview:${Versions.Libs.Android.cardView}"
            )
            val annotation = AndroidLibrary(
                name = "androidx.annotation:annotation:${Versions.Libs.Android.annotation}"
            )
            val paging = AndroidLibrary(
                name = "android.arch.paging:runtime:${Versions.Libs.Android.paging}"
            )
            val reflect = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-reflect:${Versions.Common.kotlinStdLib}"
            )
            val playCore = AndroidLibrary(
                name = "com.google.android.play:core:${Versions.Libs.Android.playCore}"
            )
            val billing = AndroidLibrary(
                name = "com.android.billingclient:billing:${Versions.Libs.Android.billing}"
            )
            val billingKtx = AndroidLibrary(
                name = "com.android.billingclient:billing-ktx:${Versions.Libs.Android.billing}"
            )
            val publisher = AndroidLibrary(
                name = "com.google.apis:google-api-services-androidpublisher:${Versions.Libs.Android.publisher}"
            )
            val firebaseFirestore = AndroidLibrary(
                name = "com.google.firebase:firebase-firestore-ktx:${Versions.Libs.Android.firebaseFirestore}"
            )
            val oauth2 = AndroidLibrary(
                name = "com.google.auth:google-auth-library-oauth2-http:${Versions.Libs.Android.auth}"
            )
            val room = AndroidLibrary(
                name = "android.arch.persistence.room:rxjava2:${Versions.Libs.Android.room}"
            )
            val roomCompiler = KaptLibrary(
                name = "android.arch.persistence.room:compiler:${Versions.Libs.Android.room}"
            )
            val dagger = AndroidLibrary(
                name = "com.google.dagger:dagger:${Versions.Libs.Android.dagger}"
            )
            val daggerCompiler = KaptLibrary(
                name = "com.google.dagger:dagger-compiler:${Versions.Libs.Android.dagger}"
            )
            val navigation = AndroidLibrary(
                name = "androidx.navigation:navigation-fragment-ktx:${Versions.Libs.Android.navigation}"
            )
            val navigationUi = AndroidLibrary(
                name = "androidx.navigation:navigation-ui-ktx:${Versions.Libs.Android.navigation}"
            )
            val keyboard = AndroidLibrary(
                name = "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:${Versions.Libs.Android.keyboard}"
            )
            val worker = AndroidLibrary(
                name = "androidx.work:work-runtime-ktx:${Versions.Libs.Android.worker}"
            )
            val gson = AndroidLibrary(
                name = "com.google.code.gson:gson:${Versions.Libs.Android.gson}"
            )
            val horizontalSelector = AndroidLibrary(
                name = "com.github.Merseyside.horizontal-selector-view:HorizontalSelectorView:${Versions.Libs.Android.horizontalSelector}"
            )
            val cicerone = AndroidLibrary(
                name = "com.github.terrakok:cicerone:${Versions.Libs.Android.cicerone}"
            )
            val playServicesAds = AndroidLibrary(
                name = "com.google.android.gms:play-services-ads:${Versions.Libs.Android.playServicesAds}"
            )
            val firebaseCore = AndroidLibrary(
                name = "com.google.firebase:firebase-core:${Versions.Libs.Android.firebaseCore}"
            )
            val firebaseAnalytics = AndroidLibrary(
                name = "com.google.firebase:firebase-analytics:${Versions.Libs.Android.firebaseAnalytic}"
            )
            val crashlytics = AndroidLibrary(
                name = "com.crashlytics.sdk.android:crashlytics:${Versions.Libs.Android.crashlytics}"
            )
            val circleImage = AndroidLibrary(
                name = "de.hdodenhof:circleimageview:${Versions.Libs.Android.circleImage}"
            )

            object MerseyLibs {
                private val base = "com.github.Merseyside.mersey-android-library"

                val archy = AndroidLibrary(
                    name = "$base:archy:${Versions.Common.merseyLib}"
                )

                val adapters = AndroidLibrary(
                    name = "$base:adapters:${Versions.Common.merseyLib}"
                )

                val animators = AndroidLibrary(
                    name = "$base:animators:${Versions.Common.merseyLib}"
                )

                val utils = AndroidLibrary(
                    name = "$base:utils:${Versions.Common.merseyLib}"
                )
            }
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = Android.kotlinStdLib.name,
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.Common.kotlinStdLib}"
            )
            val coroutines = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Common.coroutines}",
                common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.Common.coroutines}",
                ios = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.Common.coroutines}"
            )
            val serialization = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}",
                common = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}",
                ios = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}"
            )
            val mokoMvvm = MultiPlatformLibrary(
                common = "dev.icerock.moko:mvvm:${Versions.Libs.MultiPlatform.mokoMvvm}",
                iosX64 = "dev.icerock.moko:mvvm-iosx64:${Versions.Libs.MultiPlatform.mokoMvvm}",
                iosArm64 = "dev.icerock.moko:mvvm-iosarm64:${Versions.Libs.MultiPlatform.mokoMvvm}"
            )
            val mokoResources = MultiPlatformLibrary(
                common = "dev.icerock.moko:resources:${Versions.Libs.MultiPlatform.mokoResources}",
                iosX64 = "dev.icerock.moko:resources-iosx64:${Versions.Libs.MultiPlatform.mokoResources}",
                iosArm64 = "dev.icerock.moko:resources-iosarm64:${Versions.Libs.MultiPlatform.mokoResources}"
            )
            val kodein = MultiPlatformLibrary(
                common = "org.kodein.di:kodein-di:${LibraryVersions.Libs.MultiPlatform.kodein}"
            )
            val sqlDelight = MultiPlatformLibrary(
                    common = "com.squareup.sqldelight:runtime:${Versions.Libs.MultiPlatform.sqlDelight}",
                    android = "com.squareup.sqldelight:android-driver:${Versions.Libs.MultiPlatform.sqlDelight}"
            )
            val preferences = MultiPlatformLibrary(
                common = "com.github.florent37:multiplatform-preferences:${Versions.Libs.MultiPlatform.preferences}",
                android = "com.github.florent37:multiplatform-preferences-android:${Versions.Libs.MultiPlatform.preferences}"
            )

            object MerseyLibs {
                private val base = "com.merseyside.merseyLib"

                val cleanMvvmArch = MultiPlatformLibrary(
                    common = "$base:kmp-clean-mvvm-arch:${Versions.Common.merseyLib}",
                    android = "$base:kmp-clean-mvvm-arch-android:${Versions.Common.merseyLib}"
                )

                val utils = MultiPlatformLibrary(
                    common = "$base:kmp-utils:${Versions.Common.merseyLib}",
                    android = "$base:kmp-utils-android:${Versions.Common.merseyLib}"
                )
            }
        }
    }
}