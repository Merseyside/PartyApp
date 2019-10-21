package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberItemInfo

interface ItemRepository {

    suspend fun addItem(
        eventId: Long,
        name: String,
        description: String,
        price: Long,
        membersInfo: List<MemberItemInfo>
    ): Boolean

    suspend fun getItemsByEventId(eventId: Long): List<Item>
}