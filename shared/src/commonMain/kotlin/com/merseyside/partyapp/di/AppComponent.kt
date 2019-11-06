package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.db.createDatabase
import com.merseyside.partyapp.data.db.CalcDatabase
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

expect var sqlDriver: SqlDriver?

internal val databaseModule = Kodein.Module("database") {

    bind<CalcDatabase>() with singleton {
        createDatabase(sqlDriver!!)
    }
}

@ExperimentalCoroutinesApi
internal val appComponent = Kodein {
    import(databaseModule)
}