package com.merseyside.partyapp.data.db.item

import com.merseyside.kmpMerseyLib.utils.time.getCurrentTimeMillis
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.mapper.ItemDataMapper
import com.merseyside.partyapp.utils.Logger

class ItemDao(database: CalcDatabase) {

    private val db = database.itemModelQueries

    private val itemDataMapper = ItemDataMapper()

    fun insertItem(eventId: Long, name: String, description: String, price: Double, payMember: Member, membersInfo: List<MemberInfo>) {
        db.insertItem(eventId, name, description, price, payMember, MembersModel(membersInfo), getCurrentTimeMillis())
    }

    fun changeItem(id: Long, name: String, description: String, price: Double, payMember: Member, membersInfo: List<MemberInfo>) {
        Logger.logMsg(TAG, "$id")
        db.changeItem(name, description, price, payMember, MembersModel(membersInfo), getCurrentTimeMillis(), id)
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