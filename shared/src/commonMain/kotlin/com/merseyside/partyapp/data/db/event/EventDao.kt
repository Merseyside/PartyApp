package com.merseyside.partyapp.data.db.event

import com.merseyside.merseyLib.time.Time
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.data.db.event.exception.MemberExistsException
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.data.entity.mapper.EventDataMapper

class EventDao(private val database: CalcDatabase) {

    private val query = database.eventModelQueries

    private val eventDataMapper = EventDataMapper()

    @Throws(MemberExistsException::class)
    internal fun insert(name: String, members: List<Member>, notes: String): Event {
        val membersModel = MembersModel(members)
        query.insertItem(name, membersModel, notes, Status.IN_PROCESS.toString(), Time.systemTime.millis)

        return getAll().last()
        // https://github.com/sqldelight/sqldelight/issues/5800
        //return getEventById(query.lastInsertRowId().executeAsOne())
    }

    internal fun change(
        id: Long,
        name: String? = null,
        members: List<Member>? = null,
        notes: String? = null,
        status: Status? = null
    ): Event {

        val savedMembers = query.getMembers(id).executeAsOneOrNull()
        if (savedMembers != null) {
            members?.find { member -> savedMembers.members.map { it.name }.contains(member.name) }
                ?.let { throw MemberExistsException(it.name) }
        }

        val event = getEventById(id)

        name?.let {event.name = name}
        members?.let { members.map { member -> event.members.add(member)}}
        notes?.let {event.notes = notes}
        status?.let { event.status = status }

        event.let {
            query.changeItem(it.id, it.name, MembersModel(it.members), it.notes, it.status.toString(), it.timestamp)
        }

        return event
    }

    internal fun getAll(): List<Event> {
        return query.selectAll().executeAsList().let {
            eventDataMapper.transform(it)
        }
    }

    internal fun remove(id: Long) {
        query.deleteItem(id)
    }

    internal fun getEventById(id: Long): Event {
        return query.selectById(id).executeAsOne().let { eventDataMapper.transform(it) }
    }

    internal fun deleteEvent(id: Long) {
        query.deleteItem(id)
    }
}