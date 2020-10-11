package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.Contact
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.domain.repository.EventRepository
import com.merseyside.partyapp.utils.ContentResolver

class EventRepositoryImpl(
    private val eventDao: EventDao,
    private val contentResolver: ContentResolver
) : EventRepository {

    override suspend fun deleteEvent(id: Long): Boolean {
        eventDao.deleteEvent(id)

        return true
    }

    override suspend fun getContacts(): List<Contact> {
        return contentResolver.getContacts()
    }

    override suspend fun closeEvent(id: Long): Boolean {
        eventDao.change(id, status = Status.COMPLETE)

        return true
    }

    override suspend fun addEvent(id: Long?, name: String, members: List<Member>?, notes: String): Event {
        return if (id == null) {
            eventDao.insert(name, members!!, notes)
        } else {
            eventDao.change(id, name, members, notes)
        }
    }

    override suspend fun removeEvent(id: Long): Boolean {
        eventDao.remove(id)

        return true
    }

    override suspend fun getEvents(): List<Event> {
        return eventDao.getAll()
    }

    override suspend fun getEvent(id: Long): Event {
        return eventDao.getEventById(id)
    }
}