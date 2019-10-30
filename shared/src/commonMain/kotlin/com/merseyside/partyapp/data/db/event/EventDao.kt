package com.merseyside.partyapp.data.db.event

import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.data.entity.mapper.EventDataMapper
import com.merseyside.partyapp.utils.generateId
import com.merseyside.partyapp.utils.getTimestamp

class EventDao(database: CalcDatabase) {

    private val db = database.eventModelQueries

    private val eventDataMapper = EventDataMapper()

    internal fun insert(name: String, memberNames: List<String>, notes: String) {

        val members = memberNames.map {
            Member(generateId(), it)
        }

        val membersModel = MembersModel(members)

        db.insertItem(name, membersModel, notes, Status.IN_PROCESS.toString(), getTimestamp())
    }

    internal fun change(
        id: Long,
        name: String? = null,
        memberNames: List<String>? = null,
        notes: String? = null,
        status: Status? = null
    ) {

        val event = getEventById(id)

        name?.let {event.name = name}
        memberNames?.let { memberNames.map { event.members.add( Member(it, generateId() ) )}}
        notes?.let {event.notes = notes}
        status?.let { event.status = status }

        event.let {
            db.changeItem(it.id, it.name, MembersModel(it.members), it.notes, it.status.toString(), it.timestamp)
        }
    }

    internal fun getAll(): List<Event> {
        return db.selectAll().executeAsList().let {
            eventDataMapper.transform(it)
        }
    }

    internal fun remove(id: Long) {
        db.deleteItem(id)
    }

    internal fun getEventById(id: Long): Event {
        return db.selectById(id).executeAsOne().let { eventDataMapper.transform(it) }
    }

    internal fun deleteEvent(id: Long) {
        return db.deleteItem(id)
    }
}