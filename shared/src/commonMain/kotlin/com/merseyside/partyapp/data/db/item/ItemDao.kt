package com.merseyside.partyapp.data.db.item

import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.entity.mapper.ItemDataMapper
import com.merseyside.partyapp.utils.Logger
import com.merseyside.partyapp.utils.getTimestamp

class ItemDao(database: CalcDatabase) {

    private val db = database.itemModelQueries

    private val itemDataMapper = ItemDataMapper()

    fun insertItem(eventId: Long, name: String, description: String, price: Long, payMember: MemberItemInfo, membersInfo: List<MemberItemInfo>) {
        db.insertItem(eventId, name, description, price, payMember, MembersModel(membersInfo), getTimestamp())
    }

    fun changeItem(id: Long, name: String, description: String, price: Long, payMember: MemberItemInfo, membersInfo: List<MemberItemInfo>) {
        Logger.logMsg(TAG, "$id")
        db.changeItem(name, description, price, payMember, MembersModel(membersInfo), getTimestamp(), id)
    }

    fun getItemsById(eventId: Long): List<Item> {
        return db.selectItemsByEventId(eventId).executeAsList().let {
            itemDataMapper.transform(it)
        }
    }

    fun deleteItem(id: Long) {
        db.deleteItem(id)
    }

    companion object {
        private const val TAG = "ItemDao"
    }
}