package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.di.itemComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.ItemRepository
import org.kodein.di.erased.instance

class GetItemsByEventIdInteractor : CoroutineUseCase<List<Item>, GetItemsByEventIdInteractor.Params>() {

    private val repository: ItemRepository by itemComponent.instance()

    override suspend fun executeOnBackground(params: Params?): List<Item> {
        return repository.getItemsByEventId(params!!.eventId)
    }

    data class Params(val eventId: Long)
}