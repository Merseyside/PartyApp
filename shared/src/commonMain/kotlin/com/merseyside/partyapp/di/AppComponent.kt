package com.merseyside.partyapp.di

import com.github.florent37.preferences.Preferences
import com.merseyside.partyapp.data.db.createDatabase
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.utils.ContentResolver
import com.merseyside.partyapp.utils.PreferenceHelper
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

expect var sqlDriver: SqlDriver?
expect var baseContentResolver: ContentResolver?

internal val databaseModule = DI.Module("database") {

    bind<CalcDatabase>() with singleton {
        createDatabase(sqlDriver!!)
    }
}

internal val appModule = DI.Module("app") {
    bind<Preferences>() with singleton {
        Preferences("calcPrefs")
    }

    bind<PreferenceHelper>() with singleton {
        PreferenceHelper(instance())
    }

    bind<ContentResolver>() with singleton {
        getContentResolver(baseContentResolver!!)
    }
}

@ExperimentalCoroutinesApi
internal val appComponent = DI {
    import(appModule)
    import(databaseModule)
}

fun getContentResolver(contentResolver: ContentResolver): ContentResolver {
    return contentResolver
}