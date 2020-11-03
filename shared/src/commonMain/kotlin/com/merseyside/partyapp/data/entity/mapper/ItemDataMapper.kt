package com.merseyside.partyapp.data.entity.mapper

import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberInfo
import com.merseyside.partyapp.data.db.item.MembersModel
import com.merseyside.partyapp.db.model.ItemModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ItemDataMapper {

    val json: Json by lazy {
        Json {
            isLenient = true
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }

    fun membersToStr(membersModel: MembersModel): String {
        return json.encodeToString(membersModel)
    }

    fun strToMembers(str: String): MembersModel {
        return json.decodeFromString(str)
    }

    fun MemberInfoToStr(itemInfo: MemberInfo): String {
        return json.encodeToString(itemInfo)
    }

    fun strToMemberInfo(str: String): MemberInfo {
        return json.decodeFromString(str)
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