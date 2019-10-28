package com.merseyside.partyapp.data.db.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Member(
    @SerialName("memberId")
    open val id: String,

    @SerialName("memberName")
    open val name: String
)