package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberInfo

interface ItemRepository {

    suspend fun addItem(
        id: Long?,
        eventId: Long,
        name: String,
        description: String,
        price: Double,
        payMember: Member,
        membersInfo: List<MemberInfo>
    ): Boolean

    suspend fun getItemsByEventId(eventId: Long): List<Item>

    suspend fun deleteItem(id: Long): Boolean
}