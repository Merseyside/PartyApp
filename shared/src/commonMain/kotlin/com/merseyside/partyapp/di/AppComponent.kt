package com.merseyside.partyapp.di

import com.github.florent37.preferences.Preferences
import com.merseyside.partyapp.data.db.createDatabase
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.entity.Contact
import com.merseyside.partyapp.utils.ContentResolver
import com.merseyside.partyapp.utils.PreferenceHelper
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

expect var sqlDriver: SqlDriver?
expect var baseContentResolver: ContentResolver?

internal val databaseModule = Kodein.Module("database") {

    bind<CalcDatabase>() with singleton {
        createDatabase(sqlDriver!!)
    }
}

internal val appModule = Kodein.Module("app") {
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
internal val appComponent = Kodein {
    import(appModule)
    import(databaseModule)
}

fun getContentResolver(contentResolver: ContentResolver): ContentResolver {
    return contentResolver
}