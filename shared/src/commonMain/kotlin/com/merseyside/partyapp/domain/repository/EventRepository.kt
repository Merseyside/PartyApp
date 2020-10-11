package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.Contact

interface EventRepository {

    suspend fun addEvent(
        id: Long? = null,
        name: String,
        members: List<Member>?,
        notes: String
    ): Event

    suspend fun removeEvent(id: Long): Boolean

    suspend fun getEvents(): List<Event>

    suspend fun closeEvent(id: Long): Boolean

    suspend fun getEvent(id: Long): Event

    suspend fun deleteEvent(id: Long): Boolean

    suspend fun getContacts(): List<Contact>
}