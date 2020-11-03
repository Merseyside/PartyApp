package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.instance

class GetEventsInteractor : CoroutineUseCase<List<Event>, Any>() {

    private val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Any?): List<Event> {
        return repository.getEvents()
    }
}