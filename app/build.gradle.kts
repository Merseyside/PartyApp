plugins {
    with(catalogPlugins.plugins) {
        plugin(android.application)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        id(firebase.crashlytics.id())
        plugin(kotlin.kapt)
    }
}

android {
    namespace = "com.merseyside.partyapp"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.compileMinSdk.get().toInt()
        targetSdk = androidLibs.versions.compileTargetSdk.get().toInt()

        applicationId = "com.merseyside.partyapp"

        versionCode = Application.VERSION_CODE
        versionName = Application.VERSION

        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        create("release") {
            keyAlias = getKeyAlias()
            keyPassword = getSigningPassword()
            storeFile = getKeystoreFile()
            storePassword = getStorePassword()
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ""
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    packaging {
        packagingOptions.resources.excludes.addAll(
            setOf(
                "META-INF/INDEX.LIST",
                "META-INF/*.kotlin_module",
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt"
            )
        )
    }

    sourceSets.getByName("main") {
        res.srcDir("src/main/res/")
        res.srcDir("src/main/res/layouts/fragments")
        res.srcDir("src/main/res/layouts/activity")
        res.srcDir("src/main/res/layouts/dialog")
        res.srcDir("src/main/res/layouts/views")
        res.srcDir("src/main/res/value/values-light")
        res.srcDir("src/main/res/value/values-night")
    }
}

kotlinExtension {
    setCompilerArgs(
        "-Xinline-classes",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xcontext-parameters",
        "-Xjvm-default=all" // In order to use @JvmOverloads annotation
    )
}

val commonLibs = listOf(
    common.kotlin.stdlib,
    common.serialization,
    common.coroutines
)

val androidLibz = with(androidLibs) {
    listOf(
        recyclerView,
        constraintLayout,
        material,
        fragment,
        lifecycleViewModel,
        cardView,
        dagger,
        sqldelight.driver,
        gson,
        play.ads,
        cicerone,
        insetter,
        firebase.crashlytics,
        firebase.analytics,
        firebase.firestore
    )
}

val merseyLibs = listOf(
    androidLibs.mersey.adapters,
    androidLibs.mersey.firestore.coroutines
)

dependencies {
    commonLibs.forEach(::implementation)
    androidLibz.forEach(::implementation)
    merseyLibs.forEach(::implementation)

    implementation(platform(androidLibs.firebase.bom))

    implementation(androidLibs.bundles.mersey.android)
    implementation(projects.shared)
    implementation(projects.chipsLib)

    kapt(androidLibs.dagger.compiler)

    implementation("com.github.Merseyside.horizontal-selector-view:HorizontalSelectorView:1.13")
}

apply(plugin = "com.google.gms.google-services")