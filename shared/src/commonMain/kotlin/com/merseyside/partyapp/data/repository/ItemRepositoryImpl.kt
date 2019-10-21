package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.db.item.MemberItemInfo
import com.merseyside.partyapp.domain.repository.ItemRepository

class ItemRepositoryImpl(
    private val itemDao: ItemDao
) : ItemRepository {

    override suspend fun addItem(
        eventId: Long,
        name: String,
        description: String,
        price: Long,
        membersInfo: List<MemberItemInfo>
    ): Boolean {
        itemDao.insertItem(eventId, name, description, price, membersInfo)

        return true
    }

    override suspend fun getItemsByEventId(eventId: Long): List<Item> {
        return itemDao.getItemsById(eventId)
    }
}