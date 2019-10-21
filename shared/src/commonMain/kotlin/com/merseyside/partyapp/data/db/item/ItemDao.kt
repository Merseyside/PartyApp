package com.merseyside.partyapp.data.db.item

import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.entity.mapper.ItemDataMapper
import com.merseyside.partyapp.utils.getTimestamp

class ItemDao(database: CalcDatabase) {

    private val db = database.itemModelQueries

    private val itemDataMapper = ItemDataMapper()

    fun insertItem(eventId: Long, name: String, description: String, price: Long, membersInfo: List<MemberItemInfo>) {
        db.insertItem(eventId, name, description, price, MembersModel(membersInfo), getTimestamp())
    }

    fun getItemsById(eventId: Long): List<Item> {
        return db.selectItemsByEventId(eventId).executeAsList().let {
            itemDataMapper.transform(it)
        }
    }
}