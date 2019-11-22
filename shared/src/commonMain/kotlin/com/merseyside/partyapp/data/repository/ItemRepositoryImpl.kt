package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.db.item.MemberInfo
import com.merseyside.partyapp.domain.repository.ItemRepository

class ItemRepositoryImpl(
    private val itemDao: ItemDao
) : ItemRepository {

    override suspend fun deleteItem(id: Long): Boolean {
        itemDao.deleteItem(id)

        return true
    }

    override suspend fun addItem(
        id: Long?,
        eventId: Long,
        name: String,
        description: String,
        price: Double,
        payMember: Member,
        membersInfo: List<MemberInfo>
    ): Boolean {
        if (id == null) {
            itemDao.insertItem(eventId, name, description, price, payMember, membersInfo)
        } else {
            itemDao.changeItem(id, name, description, price, payMember, membersInfo)
        }

        return true
    }

    override suspend fun getItemsByEventId(eventId: Long): List<Item> {
        return itemDao.getItemsById(eventId)
    }
}