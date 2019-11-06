package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.erased.instance

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {

    override suspend fun deleteEvent(id: Long): Boolean {
        eventDao.deleteEvent(id)

        return true
    }

    override suspend fun closeEvent(id: Long): Boolean {
        eventDao.change(id, status = Status.COMPLETE)

        return true
    }

    override suspend fun addEvent(id: Long?, name: String, memberNames: List<String>?, notes: String): Event {
        return if (id == null) {
            eventDao.insert(name, memberNames!!, notes)
        } else {
            eventDao.change(id, name, memberNames, notes)
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