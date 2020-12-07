package com.merseyside.partyapp.domain.interactor

import com.merseyside.kmpMerseyLib.domain.coroutines.CoroutineUseCase
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.instance

class CloseEventInteractor : CoroutineUseCase<Boolean, CloseEventInteractor.Params>() {

    private val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Boolean {
        return repository.closeEvent(params!!.id)
    }

    data class Params(val id: Long)
}