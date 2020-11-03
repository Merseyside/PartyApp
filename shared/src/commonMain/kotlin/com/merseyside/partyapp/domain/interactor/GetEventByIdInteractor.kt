package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.instance

class GetEventByIdInteractor : CoroutineUseCase<Event, GetEventByIdInteractor.Params>() {

    private val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Event {
        return repository.getEvent(params!!.id)
    }

    data class Params(val id: Long)
}