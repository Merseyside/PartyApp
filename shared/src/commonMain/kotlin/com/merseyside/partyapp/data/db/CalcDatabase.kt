package com.merseyside.partyapp.data.db

import com.merseyside.partyapp.data.db.event.MembersModel
import com.merseyside.partyapp.data.entity.mapper.EventDataMapper
import com.merseyside.partyapp.db.model.EventModel
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

val eventDataMapper = EventDataMapper()

fun createDatabase(driver: SqlDriver): CalcDatabase {

    val membersAdapter = object : ColumnAdapter<MembersModel, String> {
        override fun decode(databaseValue: String): MembersModel {
            return eventDataMapper.strToMembers(databaseValue)
        }

        override fun encode(value: MembersModel): String {
            return eventDataMapper.membersToStr(value)
        }
    }


    return CalcDatabase(
        driver,
        EventModelAdapter = EventModel.Adapter(
            membersAdapter = membersAdapter
        ))
}