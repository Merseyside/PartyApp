package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.db.event.Event

interface EventRepository {

    suspend fun addEvent(name: String, members: List<String>, notes: String): Boolean

    suspend fun removeEvent(id: Long): Boolean

    suspend fun getEvents(): List<Event>

    suspend fun closeEvent(id: Long): Boolean

}