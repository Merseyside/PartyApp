plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
    }
}

android {
    namespace = "com.pchmn.materialchips"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.compileMinSdk.get().toInt()
    }

    buildFeatures {
        dataBinding = true
    }
}

val androidLibz = listOf(
    androidLibs.material,
    androidLibs.androidx.core
)

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

    implementation(androidLibs.mersey.utils)

    androidLibz.forEach { lib -> implementation(lib) }
    implementation("com.github.BelooS:ChipsLayoutManager:v0.3.7")
    implementation("de.hdodenhof:circleimageview:3.0.1")
}
