package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.repository.EventRepositoryImpl
import com.merseyside.partyapp.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

@ExperimentalCoroutinesApi
internal val eventModule = Kodein.Module("event") {
    bind<EventDao>() with singleton { EventDao( instance() ) }

    bind<EventRepository>() with singleton {
        EventRepositoryImpl()
    }
}

@ExperimentalCoroutinesApi
internal val eventComponent = Kodein {
    extend(appComponent)

    import(eventModule)
}