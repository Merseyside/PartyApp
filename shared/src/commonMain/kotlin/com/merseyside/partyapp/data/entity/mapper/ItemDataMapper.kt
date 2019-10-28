package com.merseyside.partyapp.data.entity.mapper

import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberItemInfo
import com.merseyside.partyapp.data.db.item.MembersModel
import com.merseyside.partyapp.db.model.ItemModel
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

class ItemDataMapper {

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun membersToStr(membersModel: MembersModel): String {
        return Json.nonstrict.stringify(membersModel)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun strToMembers(str: String): MembersModel {
        return Json.nonstrict.parse(str)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun memberItemInfoToStr(itemInfo: MemberItemInfo): String {
        return Json.nonstrict.stringify(itemInfo)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun strToMemberItemInfo(str: String): MemberItemInfo {
        return Json.nonstrict.parse(str)
    }

    fun transform(itemsModel: List<ItemModel>): List<Item> {
        return itemsModel.map {
            Item(
                it.id,
                it.eventId,
                it.name,
                it.description,
                it.price,
                it.payMember,
                it.memberModel.members,
                it.timestamp
            )
        }
    }
}