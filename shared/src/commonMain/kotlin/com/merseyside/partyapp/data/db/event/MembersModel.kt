package com.merseyside.partyapp.data.db.event

import kotlinx.serialization.Serializable

@Serializable
data class MembersModel(
    val members: List<Member>
)