package com.merseyside.partyapp.domain.interactor

import com.merseyside.kmpMerseyLib.domain.coroutines.CoroutineUseCase
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.instance

class AddEventInteractor : CoroutineUseCase<Event, AddEventInteractor.Params>() {

    val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Event {
        return repository.addEvent(params!!.id, params.name, params.members, params.notes)
    }

    data class Params(
        val id: Long? = null,
        val name: String,
        val members: List<Member>?,
        val notes: String
    )
}