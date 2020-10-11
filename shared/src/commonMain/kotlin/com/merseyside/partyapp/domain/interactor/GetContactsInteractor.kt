package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.entity.Contact
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.erased.instance

class GetContactsInteractor : CoroutineUseCase<List<Contact>, Any>() {

    private val repository: EventRepository by eventComponent.instance()

    override suspend fun executeOnBackground(params: Any?): List<Contact> {
        return repository.getContacts()
    }
}