package com.merseyside.partyapp.data.entity.mapper

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.event.MembersModel
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.db.model.EventModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventDataMapper {

    val json: Json by lazy {
        Json {
            isLenient = true
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }

    fun transform(eventModels: List<EventModel>): List<Event> {
        return eventModels.map {
            transform(it)
        }
    }

    fun transform(eventModel: EventModel): Event {
        return eventModel.let {
            Event(
                it.id,
                it.name,
                it.members.members.toMutableList(),
                it.notes,
                Status.getStatusByString(it.status) ?: throw IllegalArgumentException(),
                it.timestamp
            )
        }
    }

    fun membersToStr(membersModel: MembersModel): String {
        return json.encodeToString(membersModel)
    }

    fun strToMembers(str: String): MembersModel {
        return json.decodeFromString(str)
    }

    fun memberToStr(membersModel: Member): String {
        return json.encodeToString(membersModel)
    }

    fun strToMember(str: String): Member {
        return json.decodeFromString(str)
    }


}