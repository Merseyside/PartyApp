package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.repository.EventRepositoryImpl
import com.merseyside.partyapp.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.*

@ExperimentalCoroutinesApi
internal val eventModule = DI.Module("event") {

    bind<EventDao>() with singleton { EventDao(instance()) }

    bind<EventRepository>() with singleton {
        EventRepositoryImpl(instance(), instance())
    }
}

@ExperimentalCoroutinesApi
internal val eventComponent = DI {
    extend(appComponent)
    import(eventModule)
}