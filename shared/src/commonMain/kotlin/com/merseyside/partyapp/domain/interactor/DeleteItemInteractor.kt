package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.di.itemComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.ItemRepository
import org.kodein.di.erased.instance

class DeleteItemInteractor : CoroutineUseCase<Boolean, DeleteItemInteractor.Params>() {

    private val repository: ItemRepository by itemComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Boolean {
        return repository.deleteItem(params!!.id)
    }

    data class Params(val id: Long)
}