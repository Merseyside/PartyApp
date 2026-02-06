package com.merseyside.partyapp.di

import android.content.Context
import android.content.SharedPreferences
import app.cash.sqldelight.db.SqlDriver
import com.merseyside.partyapp.data.db.createDatabase
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.utils.ContentResolver
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

expect var mContext: Context?
expect var sqlDriver: SqlDriver?
expect var baseContentResolver: ContentResolver?

internal val databaseModule = DI.Module("database") {

    bind<CalcDatabase>() with singleton {
        createDatabase(sqlDriver!!)
    }
}

internal val appModule = DI.Module("app") {
    bind<SharedPreferences>() with singleton {
        mContext!!.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

    bind<Settings>() with singleton {
        SharedPreferencesSettings(delegate = instance())
    }

    bind<ContentResolver>() with singleton {
        getContentResolver(baseContentResolver!!)
    }
}

internal val appComponent = DI {
    import(appModule)
    import(databaseModule)
}

fun getContentResolver(contentResolver: ContentResolver): ContentResolver {
    return contentResolver
}