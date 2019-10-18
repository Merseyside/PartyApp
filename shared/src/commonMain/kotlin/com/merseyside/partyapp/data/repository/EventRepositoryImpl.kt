package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.di.eventComponent
import com.merseyside.partyapp.domain.repository.EventRepository
import org.kodein.di.erased.instance

class EventRepositoryImpl : EventRepository {

    private val eventDao: EventDao by eventComponent.instance()

    override suspend fun closeEvent(id: Long): Boolean {
        eventDao.change(id, status = Status.COMPLETE)

        return true
    }


    override suspend fun addEvent(name: String, members: List<String>, notes: String): Boolean {
        eventDao.insert(name, members, notes)

        return true
    }

    override suspend fun removeEvent(id: Long): Boolean {
        eventDao.remove(id)

        return true
    }

    override suspend fun getEvents(): List<Event> {
        return eventDao.getAll()
    }

}