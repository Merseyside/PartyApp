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
                name = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Libs.Android.kotlinStdLib}"
            )
            val coroutinesCore = AndroidLibrary(
                name = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Libs.Android.coroutines}"
            )
            val coroutines = AndroidLibrary(
                name = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.Android.coroutines}"
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
                name = "androidx.annotation:annotation:${Versions.Libs.Android.appCompat}"
            )
            val paging = AndroidLibrary(
                name = "android.arch.paging:runtime:${Versions.Libs.Android.paging}"
            )
            val reflect = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-reflect:${Versions.Libs.Android.kotlinStdLib}"
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
                name = "ru.terrakok.cicerone:cicerone:${Versions.Libs.Android.cicerone}"
            )
            val playServicesAds = AndroidLibrary(
                name = "com.google.android.gms:play-services-ads:${Versions.Libs.Android.playServicesAds}"
            )
            val firebaseCore = AndroidLibrary(
                name = "com.google.firebase:firebase-core:${Versions.Libs.Android.firebase}"
            )
            val firebaseAnalytics = AndroidLibrary(
                name = "com.google.firebase:firebase-analytics:${Versions.Libs.Android.firebase}"
            )
            val crashlytics = AndroidLibrary(
                name = "com.crashlytics.sdk.android:crashlytics:${Versions.Libs.Android.crashlytics}"
            )
            val circleImage = AndroidLibrary(
                name = "de.hdodenhof:circleimageview:${Versions.Libs.Android.circleImage}"
            )

            object MerseyLibs {
                private val base = "com.github.Merseyside.mersey-android-library"

                val cleanMvvmArch = AndroidLibrary(
                    name = "$base:clean-mvvm-arch:${Versions.Libs.MerseyLibs.version}:navigation@aar"
                )

                val adapters = AndroidLibrary(
                    name = "$base:adapters:${Versions.Libs.MerseyLibs.version}"
                )

                val animators = AndroidLibrary(
                    name = "$base:animators:${Versions.Libs.MerseyLibs.version}"
                )

                val utils = AndroidLibrary(
                    name = "$base:utils:${Versions.Libs.MerseyLibs.version}"
                )
            }
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = Android.kotlinStdLib.name,
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.Libs.MultiPlatform.kotlinStdLib}"
            )
            val coroutines = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.MultiPlatform.coroutines}",
                common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.Libs.MultiPlatform.coroutines}",
                ios = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.Libs.MultiPlatform.coroutines}"
            )
            val serialization = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.Libs.MultiPlatform.serialization}",
                common = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.Libs.MultiPlatform.serialization}",
                ios = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.Libs.MultiPlatform.serialization}"
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
                    common = "org.kodein.di:kodein-di-core:${Versions.Libs.MultiPlatform.kodein}"
            )
            val kodeinErased = MultiPlatformLibrary(
                    common = "org.kodein.di:kodein-di-erased:${Versions.Libs.MultiPlatform.kodein}"
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
                    common = "$base:kmp-clean-mvvm-arch:${Versions.Libs.MerseyLibs.version}",
                    android = "$base:kmp-clean-mvvm-arch-android:${Versions.Libs.MerseyLibs.version}"
                )

                val utils = MultiPlatformLibrary(
                    common = "$base:kmp-utils:${Versions.Libs.MerseyLibs.version}",
                    android = "$base:kmp-utils-android:${Versions.Libs.MerseyLibs.version}"
                )
            }
        }
    }

    val plugins: Map<String, String> = mapOf(
        "kotlin-android-extensions" to Plugins.androidExtensions,
        "kotlinx-serialization" to Plugins.kotlinSerialization,
        "dev.icerock.mobile.multiplatform-resources" to Plugins.mokoResources
    )
}