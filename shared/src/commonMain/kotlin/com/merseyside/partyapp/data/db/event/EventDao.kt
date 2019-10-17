package com.merseyside.partyapp.data.db.event

import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.entity.mapper.EventDataMapper
import com.merseyside.partyapp.utils.getTimestamp

class EventDao(database: CalcDatabase) {

    private val db = database.eventModelQueries

    private val eventDataMapper = EventDataMapper()

    internal fun insert(name: String, memberNames: List<String>) {

        val members = memberNames.map {
            Member(it)
        }
        val membersModel = MembersModel(members)

        db.insertItem(name, membersModel, getTimestamp())
    }

    internal fun getAll(): List<Event> {
        return db.selectAll().executeAsList().let {
            eventDataMapper.transform(it)
        }
    }

    internal fun remove(id: Long) {
        db.deleteItem(id)
    }
}