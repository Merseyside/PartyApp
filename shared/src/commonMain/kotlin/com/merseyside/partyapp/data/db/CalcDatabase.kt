package com.merseyside.partyapp.data.db

import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.event.MembersModel
import com.merseyside.partyapp.data.entity.mapper.EventDataMapper
import com.merseyside.partyapp.data.entity.mapper.ItemDataMapper
import com.merseyside.partyapp.db.model.EventModel
import com.merseyside.partyapp.db.model.ItemModel
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver



fun createDatabase(driver: SqlDriver): CalcDatabase {

    val eventDataMapper = EventDataMapper()
    val itemDataMapper = ItemDataMapper()

    val membersAdapter = object : ColumnAdapter<MembersModel, String> {
        override fun decode(databaseValue: String): MembersModel {
            return eventDataMapper.strToMembers(databaseValue)
        }

        override fun encode(value: MembersModel): String {
            return eventDataMapper.membersToStr(value)
        }
    }

    val membersModelAdapter = object : ColumnAdapter<com.merseyside.partyapp.data.db.item.MembersModel, String> {
        override fun decode(databaseValue: String): com.merseyside.partyapp.data.db.item.MembersModel {
            return itemDataMapper.strToMembers(databaseValue)
        }

        override fun encode(value: com.merseyside.partyapp.data.db.item.MembersModel): String {
            return itemDataMapper.membersToStr(value)
        }
    }

    val memberAdapter = object : ColumnAdapter<Member, String> {
        override fun decode(databaseValue: String): Member {
            return eventDataMapper.strToMember(databaseValue)
        }

        override fun encode(value: Member): String {
            return eventDataMapper.memberToStr(value)
        }
    }



    return CalcDatabase(
        driver,
        EventModelAdapter = EventModel.Adapter(
            membersAdapter = membersAdapter
        ), ItemModelAdapter = ItemModel.Adapter(
            memberModelAdapter = membersModelAdapter,
            payMemberAdapter = memberAdapter
        )
    )
}