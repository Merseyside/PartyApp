package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.instance

class DeleteEventInteractor : CoroutineUseCase<Boolean, DeleteEventInteractor.Params>() {

    private val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Boolean {
        return repository.deleteEvent(params!!.id)
    }

    data class Params(val id: Long)
}