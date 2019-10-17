package com.merseyside.partyapp.data.entity.mapper

import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.MembersModel
import com.merseyside.partyapp.db.model.EventModel
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

class EventDataMapper {

    fun transform(eventModels: List<EventModel>): List<Event> {
        return eventModels.map {
            it.let {
                Event(it.id, it.name, it.members.members, it.timestamp)
            }
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun membersToStr(membersModel: MembersModel): String {
        return Json.nonstrict.stringify(membersModel)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun strToMembers(str: String): MembersModel {
        return Json.nonstrict.parse(str)
    }
}