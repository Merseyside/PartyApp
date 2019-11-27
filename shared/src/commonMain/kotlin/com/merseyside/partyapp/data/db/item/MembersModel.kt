package com.merseyside.partyapp.data.db.item

import kotlinx.serialization.Serializable

@Serializable
data class MembersModel(
    val members: List<MemberInfo>
)