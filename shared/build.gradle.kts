plugins {
    with(catalogPlugins.plugins) {
        plugin(android.kotlin.multiplatform.library)
        plugin(kotlin.multiplatform)
        plugin(kotlin.serialization)
        plugin(sqldelight)
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
    }
}

kotlin {
    androidLibrary {
        namespace = "com.merseyside.partyapp"
        compileSdk = androidLibs.versions.compileSdk.get().toInt()

        minSdk = androidLibs.versions.compileMinSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            val commonLibs = listOf(
                common.kotlin.stdlib,
                common.serialization,
                common.coroutines
            ).forEach(::implementation)


            val mppLibs = listOf(
                multiplatformLibs.kodein,
                multiplatformLibs.sqldelight,
                multiplatformLibs.settings
            ).forEach(::implementation)

            implementation(multiplatformLibs.bundles.merseyLibs)
            implementation(common.mersey.time)
        }
    }
}

sqldelight {
    databases {
        create("CalcDatabase") {
            packageName.set("com.merseyside.partyapp.data.db")
            schemaOutputDirectory = file("build/dbs")
            dialect(multiplatformLibs.sqldelight.dialects.sqlite)
        }
    }
    linkSqlite = true
}