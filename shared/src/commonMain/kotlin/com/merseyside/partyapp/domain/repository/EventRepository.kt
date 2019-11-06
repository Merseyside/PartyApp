package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.db.event.Event

interface EventRepository {

    suspend fun addEvent(
        id: Long? = null,
        name: String,
        memberNames: List<String>?,
        notes: String
    ): Event

    suspend fun removeEvent(id: Long): Boolean

    suspend fun getEvents(): List<Event>

    suspend fun closeEvent(id: Long): Boolean

    suspend fun getEvent(id: Long): Event

    suspend fun deleteEvent(id: Long): Boolean
}