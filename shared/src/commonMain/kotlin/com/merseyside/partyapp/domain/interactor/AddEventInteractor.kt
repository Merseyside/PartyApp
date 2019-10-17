package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.erased.instance

class AddEventInteractor : CoroutineUseCase<Boolean, AddEventInteractor.Params>() {

    val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Boolean {
        return repository.addEvent(params!!.name, params.memberNames)
    }

    data class Params(val name: String, val memberNames: List<String>)
}