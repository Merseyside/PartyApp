package com.merseyside.partyapp.data.db.event

import com.merseyside.partyapp.utils.generateId
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val name: String
) {

    val id: Long = generateId()
}