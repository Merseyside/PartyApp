package com.merseyside.partyapp.data.db.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Member(
    @SerialName("memberId")
    open val id: String,

    @SerialName("memberName")
    open val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Member

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Member(ownerId='$id', name='$name')"
    }
}