package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.db.item.MemberItemInfo
import com.merseyside.partyapp.di.itemComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.ItemRepository
import org.kodein.di.erased.instance

class AddItemInteractor : CoroutineUseCase<Boolean, AddItemInteractor.Params>() {

    private val repository: ItemRepository by itemComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Boolean {
        return repository.addItem(
            params!!.id,
            params.eventId,
            params.name,
            params.description,
            params.price,
            params.payMember,
            params.membersInfo
        )
    }

    data class Params(
        val id: Long?,
        val eventId: Long,
        val name: String,
        val description: String,
        val price: Double,
        val payMember: MemberItemInfo,
        val membersInfo: List<MemberItemInfo>
    )
}