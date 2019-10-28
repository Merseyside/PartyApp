package com.merseyside.partyapp.data.db.item

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class MemberItemInfo(
    override val id: String,
    override val name: String,
    val percent: Float
) : Member(id = id, name = name)