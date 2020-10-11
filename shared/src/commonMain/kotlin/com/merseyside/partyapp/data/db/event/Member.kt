package com.merseyside.partyapp.data.db.event

import kotlinx.serialization.Serializable

@Serializable
open class Member(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val phone: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Member

        if (id != other.id) return false
        if (name != other.name) return false
        if (avatarUrl != other.avatarUrl) return false
        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + avatarUrl.hashCode()
        result = 31 * result + phone.hashCode()
        return result
    }

    override fun toString(): String {
        return "Member(id='$id', name='$name', avatarUrl='$avatarUrl', phone='$phone')"
    }
}